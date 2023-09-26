package net.nickotyn.myfirstmod.event;


import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import net.nickotyn.myfirstmod.MyFirstMod;
import net.nickotyn.myfirstmod.block.GemInfusingStationBlock;
import net.nickotyn.myfirstmod.block.entity.GemInfusingStationBlockEntity;
import net.nickotyn.myfirstmod.item.ModItems;
import net.nickotyn.myfirstmod.villager.ModVillagers;

import java.util.List;

@Mod.EventBusSubscriber(modid = MyFirstMod.MOD_ID)

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

    @SubscribeEvent
    public void onPlayerClick(TickEvent.PlayerTickEvent event, ServerLevel level) {

        Player player = event.player;
        int hitDistance = 3;

        if (event.phase == TickEvent.Phase.START && event.side.isServer() && event.player.isCrouching() && event.player.getMainHandItem().isEmpty()) {
            HitResult pos = player.pick(3, 0, false);
            BlockPos blockpos = ((BlockHitResult) pos).getBlockPos();
            if (level.getBlockState(blockpos).getBlock() instanceof GemInfusingStationBlock) {
                GemInfusingStationBlockEntity gemInfusingStationBlockEntity = new GemInfusingStationBlockEntity(blockpos, level.getBlockState(blockpos));

                if (gemInfusingStationBlockEntity instanceof GemInfusingStationBlockEntity) {
                    GemInfusingStationBlockEntity gemInfusingStationBlock = (GemInfusingStationBlockEntity) gemInfusingStationBlockEntity;

                    int slotIndex = 4;
                    LazyOptional<IItemHandler> itemHandler = gemInfusingStationBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER);

                    if (itemHandler.isPresent()) {
                        ItemStack itemStackInSlot = itemHandler.orElseThrow(NullPointerException::new).getStackInSlot(slotIndex);

                        if (!itemStackInSlot.isEmpty()) {
                            // Transfer the item to the player's main hand
                            player.setItemInHand(InteractionHand.MAIN_HAND, itemStackInSlot.copy());
                            itemHandler.orElseThrow(NullPointerException::new).insertItem(slotIndex, ItemStack.EMPTY, false);
                        }
                    }
                }
            }
        }
    }
}