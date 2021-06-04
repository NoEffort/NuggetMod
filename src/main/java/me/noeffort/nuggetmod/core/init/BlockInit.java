package me.noeffort.nuggetmod.core.init;

import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.common.block.TimePedestalBlock;
import me.noeffort.nuggetmod.common.block.WeatherPedestalBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, NuggetMod.MOD_ID);

    public static final RegistryObject<Block> UNOBTAINIUM_BLOCK = BLOCKS.register("unobtainium_block", () ->
            new Block(AbstractBlock.Properties.of(Material.HEAVY_METAL, MaterialColor.COLOR_PURPLE).harvestTool(ToolType.PICKAXE)
                    .sound(SoundType.METAL).strength(7.0F)));

    public static final RegistryObject<Block> VIBRANIUM_BLOCK = BLOCKS.register("vibranium_block", () ->
            new Block(AbstractBlock.Properties.of(Material.HEAVY_METAL, MaterialColor.COLOR_CYAN).harvestTool(ToolType.PICKAXE)
                    .sound(SoundType.METAL).strength(7.0F)));

    public static final RegistryObject<Block> ALLTHEMODIUM_BLOCK = BLOCKS.register("allthemodium_block", () ->
            new Block(AbstractBlock.Properties.of(Material.HEAVY_METAL, MaterialColor.COLOR_ORANGE).harvestTool(ToolType.PICKAXE)
                    .sound(SoundType.METAL).strength(7.0F)));

    public static final RegistryObject<FlowingFluidBlock> MOLTEN_VIBRANIUM_BLOCK = BLOCKS.register("molten_vibranium_block",
            () -> new FlowingFluidBlock(FluidInit.MOLTEN_VIBRANIUM, AbstractBlock.Properties.of(Material.LAVA, MaterialColor.EMERALD)
                .noDrops().strength(100.0F).lightLevel((state) -> 10)));

    public static final RegistryObject<WeatherPedestalBlock> WEATHER_PEDESTAL = BLOCKS.register("weather_pedestal",
            WeatherPedestalBlock::new);

    public static final RegistryObject<TimePedestalBlock> TIME_PEDESTAL = BLOCKS.register("time_pedestal",
            TimePedestalBlock::new);

}
