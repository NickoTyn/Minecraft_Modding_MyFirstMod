package net.nickotyn.myfirstmod.event;


import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
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
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import net.nickotyn.myfirstmod.MyFirstMod;
import net.nickotyn.myfirstmod.block.GemInfusingStationBlock;
import net.nickotyn.myfirstmod.block.entity.GemInfusingStationBlockEntity;
import net.nickotyn.myfirstmod.item.ModItems;
import net.nickotyn.myfirstmod.villager.ModVillagers;
import org.lwjgl.glfw.GLFW;

import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    static boolean isRightClicking = false;
    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getItemStack().isEmpty() && event.getHand() == InteractionHand.MAIN_HAND) {
            isRightClicking = true;
        }
    }
    @SubscribeEvent
    public static void onPlayerClick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {

            if (isRightClicking && event.player.isCrouching()) {
                MinecraftServer server = player.getServer();
                ServerLevel level = Objects.requireNonNull(player.getServer()).getLevel(ServerLevel.OVERWORLD);

                if (level != null) {
                    BlockPos blockpos = ((BlockHitResult) player.pick(3, 0, false)).getBlockPos();

                    if (level.getBlockState(blockpos).getBlock() instanceof GemInfusingStationBlock) {
                        GemInfusingStationBlockEntity gemInfusingStationBlockEntity = level.getBlockEntity(blockpos) instanceof GemInfusingStationBlockEntity ? (GemInfusingStationBlockEntity) level.getBlockEntity(blockpos) : null;

                        if (gemInfusingStationBlockEntity instanceof GemInfusingStationBlockEntity) {
                            int slotIndex = 4;
                            LazyOptional<IItemHandler> itemHandler = gemInfusingStationBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER);

                            if (itemHandler.isPresent()) {
                                ItemStack itemStackInSlot = itemHandler.orElseThrow(NullPointerException::new).getStackInSlot(slotIndex);

                                if (!itemStackInSlot.isEmpty()) {
                                    // Transfer the item to the player's main hand
                                    player.setItemInHand(InteractionHand.MAIN_HAND, itemStackInSlot.copy());
                                    itemHandler.orElseThrow(NullPointerException::new).insertItem(slotIndex, ItemStack.EMPTY, false);
                                    itemHandler.orElseThrow(NullPointerException::new).extractItem(slotIndex,1,false);
                                }
                            }
                        }
                    }
                }
            }

            isRightClicking = false;
        }
    }
}