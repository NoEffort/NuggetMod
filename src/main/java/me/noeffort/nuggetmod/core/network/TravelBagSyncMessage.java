package me.noeffort.nuggetmod.core.network;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.ItemStackHandler;

import java.util.function.Supplier;

public class TravelBagSyncMessage {

    private CompoundNBT data;

    public TravelBagSyncMessage() { }

    public TravelBagSyncMessage(CompoundNBT data) {
        this.data = data;
    }

    public static void encode(TravelBagSyncMessage message, PacketBuffer buffer) {
        buffer.writeNbt(message.data);
    }

    public static TravelBagSyncMessage decode(PacketBuffer buffer) {
        return new TravelBagSyncMessage(buffer.readNbt());
    }

    public static void handle(TravelBagSyncMessage message, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            ItemStackHandler handler = new ItemStackHandler();
            handler.deserializeNBT(message.data);
        });
        ctx.setPacketHandled(true);
    }

}
