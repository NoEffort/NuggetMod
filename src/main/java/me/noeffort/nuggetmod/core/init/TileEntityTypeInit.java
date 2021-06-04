package me.noeffort.nuggetmod.core.init;

import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.common.tileentity.TimePedestalTileEntity;
import me.noeffort.nuggetmod.common.tileentity.WeatherPedestalTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeInit {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, NuggetMod.MOD_ID);

    public static RegistryObject<TileEntityType<WeatherPedestalTileEntity>> WEATHER_PEDESTAL_TILE_ENTITY =
            TILE_ENTITY_TYPES.register("weather_pedestal", () -> TileEntityType.Builder.of(WeatherPedestalTileEntity::new,
                    BlockInit.WEATHER_PEDESTAL.get()).build(null));

    public static RegistryObject<TileEntityType<TimePedestalTileEntity>> TIME_PEDESTAL_TILE_ENTITY =
            TILE_ENTITY_TYPES.register("time_pedestal", () -> TileEntityType.Builder.of(TimePedestalTileEntity::new,
                    BlockInit.TIME_PEDESTAL.get()).build(null));

}
