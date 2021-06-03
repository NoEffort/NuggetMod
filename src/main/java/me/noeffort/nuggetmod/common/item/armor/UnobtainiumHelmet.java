package me.noeffort.nuggetmod.common.item.armor;

import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.core.init.ItemInit;
import me.noeffort.nuggetmod.util.Format;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber(modid = NuggetMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class UnobtainiumHelmet extends ArmorItem {

    public UnobtainiumHelmet(IArmorMaterial material, Properties properties) {
        super(material, EquipmentSlotType.HEAD, properties);
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
        tooltip.add(Format.translate(Format.Type.TOOLTIP, "elytra_protection").withStyle(TextFormatting.DARK_GRAY));
        tooltip.add(Format.translate(Format.Type.TOOLTIP, "water_breathing").withStyle(TextFormatting.DARK_GRAY));
        super.appendHoverText(item, world, tooltip, flag);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if(stack.getItem().equals(ItemInit.UNOBTAINIUM_HELMET.get()) && !world.isClientSide()) {
            if(player.isSwimming() && player.isUnderWater()) player.setAirSupply(300);
        }
        super.onArmorTick(stack, world, player);
    }

    @SubscribeEvent
    public static void onAttackEntity(LivingAttackEvent event) {
        if(event.getEntityLiving().level.isClientSide()) return;
        for(ItemStack armor : event.getEntityLiving().getArmorSlots()) {
            if(armor.getItem().equals(ItemInit.UNOBTAINIUM_HELMET.get())) {
                if(event.getSource().equals(DamageSource.FLY_INTO_WALL)) event.setCanceled(true);
                if(event.getSource().equals(DamageSource.DROWN)) {
                    event.getEntityLiving().setAirSupply(event.getEntityLiving().getMaxAirSupply());
                    event.setCanceled(true);
                }
            }
        }
    }

}
