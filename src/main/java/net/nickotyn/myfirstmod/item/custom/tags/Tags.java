package net.nickotyn.myfirstmod.item.custom.tags;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.nickotyn.myfirstmod.MyFirstMod;

import java.util.stream.Stream;

public class Tags {

    private static final ResourceLocation MOULD =
            new ResourceLocation("data/minecraft/tags/items/mould_tag.json");

    public static final TagKey<Item> MOULDS = ItemTags.create(new ResourceLocation(MyFirstMod.MOD_ID,"tags/items/mould_tag.json"));

}
