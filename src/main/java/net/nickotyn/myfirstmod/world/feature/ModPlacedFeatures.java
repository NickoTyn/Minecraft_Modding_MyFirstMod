package net.nickotyn.myfirstmod.world.feature;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.nickotyn.myfirstmod.MyFirstMod;



import java.util.List;


public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, MyFirstMod.MOD_ID);

    public static final RegistryObject<PlacedFeature> ZIRCON_ORE_PLACED = PLACED_FEATURES.register("zircon_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.ZIRCON_ORE.getHolder().get(),
                    commonOrePlacement(2, //VeinsPerChunk
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(-80),VerticalAnchor.absolute(80)))));

    public static final RegistryObject<PlacedFeature> AQUAMARINE_ORE_PLACED = PLACED_FEATURES.register("aquamarine_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.AQUAMARINE_ORE.getHolder().get(),
                    commonOrePlacement(2, //VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(-80),VerticalAnchor.absolute(80)))));

    public static final RegistryObject<PlacedFeature> PERIDOT_ORE_PLACED = PLACED_FEATURES.register("peridot_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.PERIDOT_ORE.getHolder().get(),
                    commonOrePlacement(3, //VeinsPerChunk
                            HeightRangePlacement.uniform(VerticalAnchor.absolute(0),VerticalAnchor.absolute(110)))));

    public static final RegistryObject<PlacedFeature> ZIRCON_GEODE_PLACED = PLACED_FEATURES.register("zircon_geode_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.ZIRCON_GEODE.getHolder().get(), List.of(
                    RarityFilter.onAverageOnceEvery(50), InSquarePlacement.spread(),
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-55), VerticalAnchor.absolute(0)),
                    BiomeFilter.biome())));



    public static List<PlacementModifier> orePlacement(PlacementModifier placementModifier, PlacementModifier placementModifier1) {
        return List.of(placementModifier, InSquarePlacement.spread(), placementModifier1, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int i, PlacementModifier placementModifier) {
        return orePlacement(CountPlacement.of(i), placementModifier);
    }

    public static List<PlacementModifier> rareOrePlacement(int i, PlacementModifier placementModifier) {
        return orePlacement(RarityFilter.onAverageOnceEvery(i), placementModifier);
    }


    public static void register(IEventBus eventBus){
        PLACED_FEATURES.register(eventBus);
    }
}
