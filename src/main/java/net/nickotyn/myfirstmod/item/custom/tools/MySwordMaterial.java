package net.nickotyn.myfirstmod.item.custom.tools;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.nickotyn.myfirstmod.item.ModItems;

public class MySwordMaterial implements Tier {
    @Override
    public int getUses() {
        return 1000; // Number of uses before breaking
    }

    @Override
    public float getSpeed() {
        return 10.0f; // Mining speed
    }

    @Override
    public float getAttackDamageBonus() {
        return 5.0f; // Attack damage
    }

    @Override
    public int getLevel() {
        return 3; // Enchantment level
    }

    @Override
    public int getEnchantmentValue() {
        return 15; // Enchantment value
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(ModItems.ZIRCON.get());
    }
}

