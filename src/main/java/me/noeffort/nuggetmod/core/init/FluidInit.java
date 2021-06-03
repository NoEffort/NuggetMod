package me.noeffort.nuggetmod.core.init;

import me.noeffort.nuggetmod.NuggetMod;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidInit {

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, NuggetMod.MOD_ID);

    private static final ResourceLocation VIBRANIUM_SOURCE = NuggetMod.location("blocks/fluids/vibranium/still");
    private static final ResourceLocation VIBRANIUUM_FLOWING = NuggetMod.location("blocks/fluids/vibranium/flowing");

    public static final RegistryObject<ForgeFlowingFluid.Source> MOLTEN_VIBRANIUM = FLUIDS.register("molten_vibranium",
            () -> new ForgeFlowingFluid.Source(new ForgeFlowingFluid.Properties(FluidInit.MOLTEN_VIBRANIUM,
                    FluidInit.FLOWING_MOLTEN_VIBRANIUM, FluidAttributes.builder(VIBRANIUM_SOURCE, VIBRANIUUM_FLOWING)
                    .overlay(VIBRANIUM_SOURCE).color(-14229880)).bucket(ItemInit.MOLTEN_VIBRANIUM_BUCKET)
                    .block(BlockInit.MOLTEN_VIBRANIUM_BLOCK)));

    public static final RegistryObject<ForgeFlowingFluid.Flowing> FLOWING_MOLTEN_VIBRANIUM = FLUIDS.register("flowing_molten_vibranium",
            () -> new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(FluidInit.MOLTEN_VIBRANIUM,
                    FluidInit.FLOWING_MOLTEN_VIBRANIUM, FluidAttributes.builder(VIBRANIUM_SOURCE, VIBRANIUUM_FLOWING)
                    .overlay(VIBRANIUM_SOURCE).color(-14229880)).bucket(ItemInit.MOLTEN_VIBRANIUM_BUCKET)
                    .block(BlockInit.MOLTEN_VIBRANIUM_BLOCK)));

}
