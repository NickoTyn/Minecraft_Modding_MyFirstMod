package net.nickotyn.myfirstmod.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nickotyn.myfirstmod.MyFirstMod;
import net.nickotyn.myfirstmod.block.ModBlocks;
import net.nickotyn.myfirstmod.item.custom.EightBallItem;
import net.nickotyn.myfirstmod.item.custom.tools.CustomSwordMaterials;
import net.nickotyn.myfirstmod.item.custom.tools.MySwordItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MyFirstMod.MOD_ID);

    public static final RegistryObject<Item> ZIRCON = ITEMS.register("zircon",()
            -> new Item(new Item.Properties().tab(ModCreativeModeTab.MYFIRSTMOD_TAB)));

    public static final RegistryObject<Item> AQUAMARINE = ITEMS.register("aquamarine",()
            -> new Item(new Item.Properties().tab(ModCreativeModeTab.MYFIRSTMOD_TAB)));

    public static final RegistryObject<Item> RAW_ZIRCON = ITEMS.register("raw_zircon", ()
            -> new Item(new Item.Properties().tab(ModCreativeModeTab.MYFIRSTMOD_TAB)));

    /*public static final RegistryObject<Item> RAW_AQUAMARINE = ITEMS.register("raw_aquamarine", ()
            -> new Item(new Item.Properties().tab(ModCreativeModeTab.MYFIRSTMOD_TAB)));*/

    public static final RegistryObject<Item> EIGHT_BALL = ITEMS.register("eight_ball", ()
            -> new EightBallItem(new Item.Properties().tab(ModCreativeModeTab.MYFIRSTMOD_TAB).stacksTo(1)));

    public static final RegistryObject<Item> BLUEBERRY_SEEDS = ITEMS.register("blueberry_seeds", ()
            -> new ItemNameBlockItem(ModBlocks.BLUEBERRY_CROP.get(),
                new Item.Properties().tab(ModCreativeModeTab.MYFIRSTMOD_TAB)));

    public static final RegistryObject<Item> BLUEBERRY = ITEMS.register("blueberry", ()
            -> new Item(new Item.Properties().tab(ModCreativeModeTab.MYFIRSTMOD_TAB).food(new FoodProperties.Builder().nutrition(2).saturationMod(2f).build())));

    public static final RegistryObject<Item> MY_SWORD_MATERIAL1 = ITEMS.register("zircon_sword", ()
            -> new MySwordItem(CustomSwordMaterials.ZIRCON, 0, -2,
            new Item.Properties().tab(ModCreativeModeTab.MYFIRSTMOD_TAB)));

    public static void register(IEventBus eventbus){
        ITEMS.register(eventbus);
    }


}
