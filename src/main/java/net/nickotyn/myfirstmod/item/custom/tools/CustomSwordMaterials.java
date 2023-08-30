package net.nickotyn.myfirstmod.item.custom.tools;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.nickotyn.myfirstmod.item.ModItems;

public enum CustomSwordMaterials implements Tier {
    // Define your material types here
    ZIRCON(1300, 4.0f, 0f, 3, 15);

    private final int uses;
    private final float speed;
    private final float attackDamageBonus;
    private final int level;
    private final int enchantmentValue;
    private Item repairIngredient;

    CustomSwordMaterials(int uses, float attackDamageBonus, float speed, int level, int enchantmentValue) {
        this.uses = uses;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.level = level;
        this.enchantmentValue = enchantmentValue;
    }

    static {
        for (CustomSwordMaterials material : values()) {
            // Initialize repair item references here
            material.repairIngredient = ModItems.ZIRCON.get();
        }
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamageBonus;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(ModItems.ZIRCON.get());
        }
    }