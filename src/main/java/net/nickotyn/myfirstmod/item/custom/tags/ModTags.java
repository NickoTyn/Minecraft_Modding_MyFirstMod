package net.nickotyn.myfirstmod.item.custom.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.nickotyn.myfirstmod.MyFirstMod;

public class ModTags {


    public static class Items{

        public static final TagKey<Item> MOULDS = tag("mould_tag");


        private  static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(MyFirstMod.MOD_ID, name));
        }
    }

}
