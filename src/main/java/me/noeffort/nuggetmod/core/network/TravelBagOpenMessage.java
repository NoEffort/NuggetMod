package me.noeffort.nuggetmod.core.network;

import me.noeffort.nuggetmod.common.item.TravelBagItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TravelBagOpenMessage {

    private ItemStack item;

    public TravelBagOpenMessage() { }

    public TravelBagOpenMessage(ItemStack item) {
        this.item = item;
    }

    public static void encode(TravelBagOpenMessage message, PacketBuffer buffer) {
        buffer.writeItemStack(message.item, false);
    }

    public static TravelBagOpenMessage decode(PacketBuffer buffer) {
        return new TravelBagOpenMessage(buffer.readItem());
    }

    public static void handle(TravelBagOpenMessage message, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        if(!(message.item.getItem() instanceof TravelBagItem)) return;
        ctx.enqueueWork(() -> {
            TravelBagItem bag = (TravelBagItem) message.item.getItem();
            ServerPlayerEntity player = ctx.getSender();
            bag.openGui(player, message.item, Hand.OFF_HAND);
        });
        ctx.setPacketHandled(true);
    }

}
