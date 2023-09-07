package net.nickotyn.myfirstmod.world.feature;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.nickotyn.myfirstmod.MyFirstMod;
import net.minecraft.world.level.block.Blocks;
import net.nickotyn.myfirstmod.block.ModBlocks;


import java.util.List;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ? >> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, MyFirstMod.MOD_ID);


    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_ZIRCON_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.ZIRCON_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_ZIRCON_ORE.get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_AQUAMARINE_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.AQUAMARINE_ORE.get().defaultBlockState()),
           OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_AQUAMARINE_ORE.get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> NETHER_PERIDOT_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES, ModBlocks.NETHERRACK_PERIDOT_ORE.get().defaultBlockState())));
    public static final RegistryObject<ConfiguredFeature<?, ?>> ZIRCON_ORE = CONFIGURED_FEATURES.register("zircon_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_ZIRCON_ORES.get(), 9))); //number per vein

    public static final RegistryObject<ConfiguredFeature<?, ?>> AQUAMARINE_ORE = CONFIGURED_FEATURES.register("aquamarine_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_AQUAMARINE_ORES.get(), 4))); //number per vein

    public static final RegistryObject<ConfiguredFeature<?, ?>> PERIDOT_ORE = CONFIGURED_FEATURES.register("netherrack_peridot_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(NETHER_PERIDOT_ORES.get(), 4))); //number per vein


    public static final RegistryObject<ConfiguredFeature<?, ?>> ZIRCON_GEODE = CONFIGURED_FEATURES.register("zircon_geode",
            () -> new ConfiguredFeature<>(Feature.GEODE,
                    new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR),        //inner Layer
                            BlockStateProvider.simple(Blocks.DEEPSLATE),                                        //alternate inner Layer
                            BlockStateProvider.simple(ModBlocks.DEEPSLATE_ZIRCON_ORE.get()),                    //middleLayer
                            BlockStateProvider.simple(Blocks.DIRT),                                             //outerLayer
                            BlockStateProvider.simple(Blocks.MOSSY_COBBLESTONE),                                //innerPlacements
                            List.of(ModBlocks.ZIRCON_BLOCK.get().defaultBlockState()),                          //cannot replace
                            BlockTags.FEATURES_CANNOT_REPLACE , BlockTags.GEODE_INVALID_BLOCKS),                //invalid blocks
                            new GeodeLayerSettings(1.7D, 1.2D, 2.5D, 3.5D),
                            new GeodeCrackSettings(0.25D, 1.5D, 1), 0.14D, 0.1D,
                            true, UniformInt.of(3, 8),
                            UniformInt.of(2, 6), UniformInt.of(1, 2),
                            -18, 18, 0.075D, 1)));



    public static void register(IEventBus eventBus){
        CONFIGURED_FEATURES.register(eventBus);
    }
}












