package me.noeffort.nuggetmod.core.network;

import me.noeffort.nuggetmod.NuggetMod;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PickBlockMessage {

    private ItemStack item;

    public PickBlockMessage() { }

    public PickBlockMessage(ItemStack item) { this.item = item; }

    public static void encode(PickBlockMessage message, PacketBuffer buffer) {
        buffer.writeItemStack(message.item, true);
    }

    public static PickBlockMessage decode(PacketBuffer buffer) {
        return new PickBlockMessage(buffer.readItem());
    }

    public static void handle(PickBlockMessage message, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            ServerPlayerEntity player = ctx.getSender();
            if(player == null) return;
            if(player.getItemInHand(Hand.MAIN_HAND).sameItem(message.item)) return;
            if(player.getItemInHand(Hand.MAIN_HAND).equals(ItemStack.EMPTY)) {
                player.setItemInHand(Hand.MAIN_HAND, message.item);
            } else {
                ItemStack replace = player.getItemInHand(Hand.MAIN_HAND);
                player.setItemInHand(Hand.MAIN_HAND, message.item);
                player.addItem(replace);
            }
        });
        ctx.setPacketHandled(true);
    }

}
