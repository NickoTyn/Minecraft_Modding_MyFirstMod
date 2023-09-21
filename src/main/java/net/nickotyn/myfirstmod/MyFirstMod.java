package net.nickotyn.myfirstmod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nickotyn.myfirstmod.block.ModBlocks;
import net.nickotyn.myfirstmod.block.entity.ModBlockEntities;
import net.nickotyn.myfirstmod.item.ModItems;
import net.nickotyn.myfirstmod.networking.ModMessages;
import net.nickotyn.myfirstmod.recipe.ModRecipes;
import net.nickotyn.myfirstmod.screen.GemInfusingStationScreen;
import net.nickotyn.myfirstmod.screen.ModMenuTypes;
//import net.nickotyn.myfirstmod.screen.ToolForgeMenu;
//import net.nickotyn.myfirstmod.screen.ToolForgeScreen;
import net.nickotyn.myfirstmod.screen.ToolForgeMenu;
import net.nickotyn.myfirstmod.screen.ToolForgeScreen;
import net.nickotyn.myfirstmod.villager.ModVillagers;
import net.nickotyn.myfirstmod.world.feature.ModConfiguredFeatures;
import net.nickotyn.myfirstmod.world.feature.ModPlacedFeatures;
import org.slf4j.Logger;

@Mod(MyFirstMod.MOD_ID)
public class MyFirstMod {
    public static final String MOD_ID = "myfirstmod";
    private static final Logger LOGGER = LogUtils.getLogger();


    public MyFirstMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    ModItems.register(modEventBus);
    ModBlocks.register(modEventBus);

    ModVillagers.register(modEventBus);

    ModConfiguredFeatures.register(modEventBus);
    ModPlacedFeatures.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }




    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            ModMessages.register();
            ModVillagers.registerPOIs();
    });

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents{
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            MenuScreens.register(ModMenuTypes.GEM_INFUSING_STATION_MENU.get(), GemInfusingStationScreen::new);
            MenuScreens.register(ModMenuTypes.TOOL_FORGE_MENU.get(), ToolForgeScreen::new);
        }
    }



}
