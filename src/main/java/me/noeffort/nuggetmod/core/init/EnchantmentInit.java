package me.noeffort.nuggetmod.core.init;

import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.common.enchantment.UpgradedPotentialsEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentInit {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, NuggetMod.MOD_ID);

    public static final RegistryObject<Enchantment> UPGRADED_POTENTIALS = ENCHANTMENTS.register("upgraded_potentials",
            UpgradedPotentialsEnchantment::new);

}
