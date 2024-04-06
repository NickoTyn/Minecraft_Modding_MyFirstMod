package net.nickotyn.myfirstmod.event;


import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import net.nickotyn.myfirstmod.MyFirstMod;
import net.nickotyn.myfirstmod.block.GemInfusingStationBlock;
import net.nickotyn.myfirstmod.block.entity.GemInfusingStationBlockEntity;
import net.nickotyn.myfirstmod.item.ModItems;
import net.nickotyn.myfirstmod.villager.ModVillagers;

import java.util.List;
import java.util.Objects;


@Mod.EventBusSubscriber(modid = MyFirstMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)

public class ModEvents {


    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {

        if (event.getType() == ModVillagers.LAMP_MASTER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.EIGHT_BALL.get(), 1);
            int villagerLevel = 1;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    stack, 10, 8, 0.02F));

        }
    }

    static boolean isRightClicking = false;

    /**
     * This event now just marks the player as having initiated a right-click action.
     *  The actual logic of whether they're holding an item or not is moved to the onPlayerClick event.
     */
    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        //
        Player player = (Player) event.getEntity();
        if (event.getHand() == InteractionHand.MAIN_HAND && player.isCrouching()) {
            isRightClicking = true;
        }
    }

    /**
     * Processes player interactions with the Gem Infusing Station block.
     * It handles item transfers between the player's main hand (ONLY MOULDS) and the station's slot based on the player's action:
     */
    @SubscribeEvent
    public static void onPlayerClick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END && isRightClicking) {
            isRightClicking = false; // Reset right-clicking state early to ensure it's only used once per action

            MinecraftServer server = player.getServer();
            ServerLevel level = Objects.requireNonNull(server).getLevel(ServerLevel.OVERWORLD);

            if (level != null) {
                BlockPos blockpos = ((BlockHitResult) player.pick(3, 0, false)).getBlockPos();

                if (level.getBlockState(blockpos).getBlock() instanceof GemInfusingStationBlock) {
                    GemInfusingStationBlockEntity gemInfusingStationBlockEntity = (GemInfusingStationBlockEntity) level.getBlockEntity(blockpos);

                    if (gemInfusingStationBlockEntity != null) {
                        LazyOptional<IItemHandler> itemHandlerOptional = gemInfusingStationBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER);
                        itemHandlerOptional.ifPresent(itemHandler -> {
                            int slotIndex = 4;
                            ItemStack itemStackInSlot = itemHandler.getStackInSlot(slotIndex);
                            ItemStack itemStackInHand = player.getItemInHand(InteractionHand.MAIN_HAND);

                            // Scenario 1: Player's hand is empty, and there's an item in the slot
                            if (itemStackInHand.isEmpty() && !itemStackInSlot.isEmpty()) {
                                // Transfer the item to the player's main hand
                                player.setItemInHand(InteractionHand.MAIN_HAND, itemStackInSlot.copy());
                                itemHandler.extractItem(slotIndex, itemStackInSlot.getCount(), false);

                                // After transferring the item, mark the block entity as needing an update
                                //gemInfusingStationBlockEntity.setChanged(); // Marks the tile entity as changed, so it gets saved
                                //level.sendBlockUpdated(blockpos, level.getBlockState(blockpos), level.getBlockState(blockpos), 3);
                                //level.sendBlockUpdated(blockpos, level.getBlockState(blockpos), level.getBlockState(blockpos), 2);
                                // This will notify clients that the block state has changed and should be visually updated

                            }
                            // Scenario 2: Player's hand has an item, and the slot can accept it
                            else if (!itemStackInHand.isEmpty()) {
                                ItemStack remaining = itemHandler.insertItem(slotIndex, itemStackInHand.copy(), false);
                                if (remaining.isEmpty() || remaining.getCount() < itemStackInHand.getCount()) {
                                    // Remove the items transferred from the player's hand
                                    itemStackInHand.shrink(itemStackInHand.getCount());
                                    player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);

                                    // After transferring the item, mark the block entity as needing an update
                                    //gemInfusingStationBlockEntity.setChanged(); // Marks the tile entity as changed, so it gets saved
                                    //level.sendBlockUpdated(blockpos, level.getBlockState(blockpos), level.getBlockState(blockpos), 3);
                                    //level.sendBlockUpdated(blockpos, level.getBlockState(blockpos), level.getBlockState(blockpos), 2);
                                    // This will notify clients that the block state has changed and should be visually updated

                                }
                            }
                        });
                    }
                }
            }
        }
    }


}









