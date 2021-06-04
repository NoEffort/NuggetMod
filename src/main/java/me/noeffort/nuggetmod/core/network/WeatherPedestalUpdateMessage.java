package me.noeffort.nuggetmod.core.network;

import me.noeffort.nuggetmod.common.tileentity.WeatherPedestalTileEntity;
import me.noeffort.nuggetmod.util.PlayerHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class WeatherPedestalUpdateMessage {

    private WeatherPedestalTileEntity.Weather weather;

    public WeatherPedestalUpdateMessage() { }

    public WeatherPedestalUpdateMessage(WeatherPedestalTileEntity.Weather weather) {
        this.weather = weather;
    }

    public static void encode(WeatherPedestalUpdateMessage message, PacketBuffer buffer) {
        buffer.writeEnum(message.weather);
    }

    public static WeatherPedestalUpdateMessage decode(PacketBuffer buffer) {
        return new WeatherPedestalUpdateMessage(buffer.readEnum(WeatherPedestalTileEntity.Weather.class));
    }

    public static void handle(WeatherPedestalUpdateMessage message, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            ServerPlayerEntity player = ctx.getSender();
            BlockPos pos = PlayerHelper.getLookingAt(player);
            if(pos == null) throw new IllegalStateException("Cannot modify invalid block!");
            TileEntity tile = player.level.getBlockEntity(pos);
            if(tile == null || !player.level.isLoaded(pos) || !(tile instanceof WeatherPedestalTileEntity))
                return;
            WeatherPedestalTileEntity pedestal = (WeatherPedestalTileEntity) tile;
            pedestal.setWeather(message.weather);
            player.level.sendBlockUpdated(pos, pedestal.getBlockState(), pedestal.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        });
        ctx.setPacketHandled(true);
    }

}
