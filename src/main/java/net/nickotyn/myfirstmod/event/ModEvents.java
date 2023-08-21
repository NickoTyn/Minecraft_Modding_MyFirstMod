package net.nickotyn.myfirstmod.event;


import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.fml.common.Mod;
import net.nickotyn.myfirstmod.MyFirstMod;
import net.nickotyn.myfirstmod.item.ModItems;
import net.nickotyn.myfirstmod.villager.ModVillagers;

import java.util.List;

@Mod.EventBusSubscriber(modid = MyFirstMod.MOD_ID)

 public class ModEvents {

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


}
