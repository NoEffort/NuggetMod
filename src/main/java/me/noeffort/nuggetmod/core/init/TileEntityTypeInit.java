package me.noeffort.nuggetmod.core.init;

import com.refinedmods.refinedstorage.tile.BaseTile;
import com.refinedmods.refinedstorage.tile.data.TileDataManager;
import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.common.tileentity.TimePedestalTileEntity;
import me.noeffort.nuggetmod.common.tileentity.WeatherPedestalTileEntity;
import me.noeffort.nuggetmod.common.tileentity.refinedstorage.CreativeWirelessTransmitterTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = NuggetMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TileEntityTypeInit {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, NuggetMod.MOD_ID);

    public static RegistryObject<TileEntityType<WeatherPedestalTileEntity>> WEATHER_PEDESTAL_TILE_ENTITY =
            TILE_ENTITY_TYPES.register("weather_pedestal", () -> TileEntityType.Builder.of(WeatherPedestalTileEntity::new,
                    BlockInit.WEATHER_PEDESTAL.get()).build(null));

    public static RegistryObject<TileEntityType<TimePedestalTileEntity>> TIME_PEDESTAL_TILE_ENTITY =
            TILE_ENTITY_TYPES.register("time_pedestal", () -> TileEntityType.Builder.of(TimePedestalTileEntity::new,
                    BlockInit.TIME_PEDESTAL.get()).build(null));

    public static TileEntityType<?> CREATIVE_WIRELESS_TRANSMITTER_TILE_ENTITY = null;

    @SubscribeEvent
    public static void onRegisterTileEntityTypes(final RegistryEvent.Register<TileEntityType<?>> event) {
        TileEntityType<?> type = TileEntityType.Builder.of(CreativeWirelessTransmitterTileEntity::new,
                BlockInit.CREATIVE_WIRELESS_TRANSMITTER.get()).build(null)
                .setRegistryName(NuggetMod.location("creative_wireless_transmitter"));
        ((BaseTile) type.create()).getDataManager().getParameters().forEach(TileDataManager::registerParameter);
        event.getRegistry().register(type);
        CREATIVE_WIRELESS_TRANSMITTER_TILE_ENTITY = type;
    }

//    public static RegistryObject<TileEntityType<CreativeWirelessTransmitterTileEntity>> CREATIVE_WIRELESS_TRANSMITTER_TILE_ENTITY =
//            TILE_ENTITY_TYPES.register("creative_wireless_transmitter", () -> TileEntityType.Builder.of(CreativeWirelessTransmitterTileEntity::new,
//                    BlockInit.CREATIVE_WIRELESS_TRANSMITTER.get()).build(null));

}
