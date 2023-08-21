package net.nickotyn.myfirstmod.villager;

import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nickotyn.myfirstmod.MyFirstMod;
import net.nickotyn.myfirstmod.block.ModBlocks;
import org.spongepowered.tools.obfuscation.fg3.ObfuscationServiceFG3;

import java.lang.reflect.InvocationTargetException;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, MyFirstMod.MOD_ID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, MyFirstMod.MOD_ID);





    public static final RegistryObject<PoiType> ZIRCON_LAMP_POI = POI_TYPES.register("zircon_lamp_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.ZIRCON_LAMP.get().getStateDefinition().getPossibleStates()),1,1));

    public static final RegistryObject<VillagerProfession> LAMP_MASTER = VILLAGER_PROFESSIONS.register("lamp_master",
            () -> new VillagerProfession("lamp_master", x -> x.get() == ZIRCON_LAMP_POI.get(), x -> x.get() == ZIRCON_LAMP_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_LIBRARIAN));


    public static void registerPOIs(){
        try{
            ObfuscationReflectionHelper.findMethod(PoiType.class,"registerBlockStates", PoiType.class).invoke(null, ZIRCON_LAMP_POI.get());

        } catch (InvocationTargetException | IllegalAccessException exception){
            exception.printStackTrace();
        }

    }

    public static void register(IEventBus eventBUS){
        POI_TYPES.register(eventBUS);
        VILLAGER_PROFESSIONS.register(eventBUS);
    }
}
