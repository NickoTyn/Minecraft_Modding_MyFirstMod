package net.nickotyn.myfirstmod.item.custom.tools;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public class MySwordItem extends SwordItem {
    public MySwordItem(Tier material, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
        super(material, attackDamageIn, attackSpeedIn, builder);
    }
}

