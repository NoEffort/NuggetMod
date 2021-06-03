package me.noeffort.nuggetmod.common.item.armor;

import me.noeffort.nuggetmod.core.init.ItemInit;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public enum ArmorMaterial implements IArmorMaterial {

    UNOBTAINIUM("unobtainium", 2000, new int[]{ 400, 600, 600, 400 }, 285,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 500.0F, () -> Ingredient.of(ItemInit.VIBRANIUM_INGOT.get()))
    ;

    private static final int[] MAX_DAMAGE = new int[]{ 400, 600, 600, 400 };

    private final String name;
    private final int damageFactor;
    private final int[] damageReduction;
    private final int enchantability;
    private final SoundEvent equip;
    private final float toughness;
    private final float knockback;
    private final LazyValue<Ingredient> repair;

    ArmorMaterial(String name, int damageFactor, int[] damageReduction, int enchantability, SoundEvent equip,
                  float toughness, Supplier<Ingredient> repair) {
        this.name = name;
        this.damageFactor = damageFactor;
        this.damageReduction = damageReduction;
        this.enchantability = enchantability;
        this.equip = equip;
        this.toughness = toughness;
        this.knockback = toughness / 100.0F;
        this.repair = new LazyValue<>(repair);
    }

    @Override
    public int getDurabilityForSlot(@Nonnull EquipmentSlotType slot) {
        return MAX_DAMAGE[slot.getIndex()] * this.damageFactor;
    }

    @Override
    public int getDefenseForSlot(@Nonnull EquipmentSlotType slot) {
        return this.damageReduction[slot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Nonnull
    @Override
    public SoundEvent getEquipSound() {
        return this.equip;
    }

    @Nonnull
    @Override
    public Ingredient getRepairIngredient() {
        return this.repair.get();
    }

    @Nonnull
    @OnlyIn(Dist.CLIENT)
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockback;
    }
}
