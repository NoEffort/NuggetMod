package me.noeffort.nuggetmod.common.item.armor;

import me.noeffort.nuggetmod.core.init.ItemInit;
import me.noeffort.nuggetmod.util.Format;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;

public class UnobtainiumLeggings extends ArmorItem {

    public UnobtainiumLeggings(IArmorMaterial material, Properties properties) {
        super(material, EquipmentSlotType.LEGS, properties);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@Nonnull ItemStack item, @Nullable World world, @Nonnull List<ITextComponent> tooltip,
                                @Nonnull ITooltipFlag flag) {
        tooltip.add(Format.translate(Format.Type.TOOLTIP, "piglin_neutral").withStyle(TextFormatting.DARK_GRAY));
        tooltip.add(Format.translate(Format.Type.TOOLTIP, "wither_protection").withStyle(TextFormatting.DARK_GRAY));
        tooltip.add(Format.translate(Format.Type.TOOLTIP, "magic_protection").withStyle(TextFormatting.DARK_GRAY));
        tooltip.add(Format.translate(Format.Type.TOOLTIP, "shulker_protection").withStyle(TextFormatting.DARK_GRAY));
        tooltip.add(Format.translate(Format.Type.TOOLTIP, "nausea_protection").withStyle(TextFormatting.DARK_GRAY));
        super.appendHoverText(item, world, tooltip, flag);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if(stack.getItem().equals(ItemInit.UNOBTAINIUM_LEGGINGS.get()) && !world.isClientSide()) {
            List<Effect> immune = Arrays.asList(Effects.CONFUSION, Effects.LEVITATION, Effects.WITHER);
            try {
                for(EffectInstance instance : player.getActiveEffects()) {
                    if(immune.contains(instance.getEffect())) {
                        Effect effect = immune.stream().filter(e -> e.equals(instance.getEffect())).findFirst()
                                .orElseThrow(ConcurrentModificationException::new);
                        player.removeEffect(effect);
                    }
                }
            } catch (ConcurrentModificationException ignore) {
            } finally {
                super.onArmorTick(stack, world, player);
            }
        }
        super.onArmorTick(stack, world, player);
    }

}
