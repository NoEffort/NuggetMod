package me.noeffort.nuggetmod.common.enchantment;

import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.core.init.EnchantmentInit;
import me.noeffort.nuggetmod.util.Format;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.Map;

@Mod.EventBusSubscriber(modid = NuggetMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class UpgradedPotentialsEnchantment extends Enchantment {

    public UpgradedPotentialsEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentType.create("none", (item) -> false),
                new EquipmentSlotType[]{ EquipmentSlotType.MAINHAND });
    }

    @Override
    public int getMinCost(int min) {
        return 35;
    }

    @Override
    public int getMaxCost(int max) {
        return super.getMinCost(max) + 45;
    }

    @Override
    public boolean canEnchant(@Nonnull ItemStack stack) {
        return false;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(@Nonnull ItemStack stack) {
        NuggetMod.LOGGER.debug("Same? " + ItemStack.isSame(stack, new ItemStack(Items.BOOK)));
        return ItemStack.isSame(stack, new ItemStack(Items.BOOK));
    }

    @Override
    public boolean isTradeable() {
        return true;
    }

    @Nonnull
    @Override
    public ITextComponent getFullname(int level) {
        return Format.translate(Format.Type.ENCHANTMENT, "upgraded_potentials")
                .withStyle(this.isCurse() ? TextFormatting.RED : TextFormatting.GRAY);
    }

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if(left.isEmpty() || right.isEmpty()) return;

        Enchantment upgraded = EnchantmentInit.UPGRADED_POTENTIALS.get();

        if(right.getItem().equals(Items.ENCHANTED_BOOK)) {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(right);
            if(enchantments.containsKey(upgraded) && enchantments.get(upgraded) >= 1) {

                if(left.isStackable()) {
                    event.setOutput(ItemStack.EMPTY);
                    return;
                }

                if(EnchantmentHelper.getEnchantments(left).size() == 0) {
                    event.setOutput(ItemStack.EMPTY);
                    return;
                }

                if(EnchantmentHelper.getItemEnchantmentLevel(upgraded, left) >= 1) {
                    event.setOutput(ItemStack.EMPTY);
                    return;
                }

                int cost = left.getBaseRepairCost();
                cost = Math.max(0, (cost / 4) - 20);

                ItemStack output = left.copy();
                output.setRepairCost(cost);
                output.enchant(upgraded, 1);

                event.setOutput(output);
                event.setCost(1);
            }
        }
    }
}
