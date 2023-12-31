package net.nickotyn.myfirstmod.event;




import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.nickotyn.myfirstmod.MyFirstMod;
import net.nickotyn.myfirstmod.block.GemInfusingStationBlock;
import net.nickotyn.myfirstmod.block.entity.GemInfusingStationBlockEntity;
import net.nickotyn.myfirstmod.block.entity.ModBlockEntities;
import net.nickotyn.myfirstmod.block.entity.renderer.GemInfusingStationBlockEntityRenderer;
import net.nickotyn.myfirstmod.block.entity.renderer.ToolForgeEntityRenderer;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = MyFirstMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

    }

    @Mod.EventBusSubscriber(modid = MyFirstMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {


        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.GEM_INFUSING_STATION.get(),
                    GemInfusingStationBlockEntityRenderer::new);

            event.registerBlockEntityRenderer(ModBlockEntities.TOOL_FORGE.get(),
                    ToolForgeEntityRenderer::new);
        }



        @Mod.EventBusSubscriber(modid = MyFirstMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
        public class YourEventHandlerClass {
        }



    }

}