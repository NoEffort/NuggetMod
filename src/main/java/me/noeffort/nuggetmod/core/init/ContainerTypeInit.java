package me.noeffort.nuggetmod.core.init;

import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.common.container.TravelBagContainer;
import me.noeffort.nuggetmod.common.container.WeatherPedestalContainer;
import me.noeffort.nuggetmod.common.item.TravelBagItem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = NuggetMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ContainerTypeInit {

    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, NuggetMod.MOD_ID);

    public static final RegistryObject<ContainerType<WeatherPedestalContainer>> WEATHER_PEDESTAL_CONTAINER_TYPE =
            CONTAINER_TYPES.register("weather_pedestal", () ->
                    IForgeContainerType.create(WeatherPedestalContainer::from));

    private static final Map<TravelBagItem.Type, ContainerType<TravelBagContainer>> TRAVEL_BAG_MAP = new HashMap<>();

    @SubscribeEvent
    @SuppressWarnings("unchecked")
    public static void onContainerTypeRegister(RegistryEvent.Register<ContainerType<?>> event) {
        for(TravelBagItem.Type type : TravelBagItem.Type.values()) {
            if(!TRAVEL_BAG_MAP.containsKey(type)) {
                ContainerType<TravelBagContainer> container = (ContainerType<TravelBagContainer>)
                        IForgeContainerType.create((int id, PlayerInventory i, PacketBuffer b) ->
                                TravelBagContainer.from(id, type, i, b)).setRegistryName("tavel_bag_" + type.getSerializedName());
                event.getRegistry().register(container);
                TRAVEL_BAG_MAP.put(type, container);
            }
        }
    }

    public static ContainerType<TravelBagContainer> getTravelBagContainer(TravelBagItem.Type type) {
        return TRAVEL_BAG_MAP.get(type);
    }

}
