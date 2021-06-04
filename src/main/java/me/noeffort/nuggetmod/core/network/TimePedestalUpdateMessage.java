package me.noeffort.nuggetmod.core.network;

import me.noeffort.nuggetmod.common.tileentity.TimePedestalTileEntity;
import me.noeffort.nuggetmod.common.tileentity.WeatherPedestalTileEntity;
import me.noeffort.nuggetmod.util.PlayerHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TimePedestalUpdateMessage {

    private TimePedestalTileEntity.Time time;

    public TimePedestalUpdateMessage() { }

    public TimePedestalUpdateMessage(TimePedestalTileEntity.Time time) {
        this.time = time;
    }

    public static void encode(TimePedestalUpdateMessage message, PacketBuffer buffer) {
        buffer.writeEnum(message.time);
    }

    public static TimePedestalUpdateMessage decode(PacketBuffer buffer) {
        return new TimePedestalUpdateMessage(buffer.readEnum(TimePedestalTileEntity.Time.class));
    }

    public static void handle(TimePedestalUpdateMessage message, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            ServerPlayerEntity player = ctx.getSender();
            BlockPos pos = PlayerHelper.getLookingAt(player);
            if(pos == null) throw new IllegalStateException("Cannot modify invalid block!");
            TileEntity tile = player.level.getBlockEntity(pos);
            if(tile == null || !player.level.isLoaded(pos) || !(tile instanceof TimePedestalTileEntity))
                return;
            TimePedestalTileEntity pedestal = (TimePedestalTileEntity) tile;
            pedestal.setTime(message.time);
            player.level.sendBlockUpdated(pos, pedestal.getBlockState(), pedestal.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        });
        ctx.setPacketHandled(true);
    }

}
