package me.noeffort.nuggetmod.common.refinedstorage.mixin;

import com.refinedmods.refinedstorage.api.network.INetwork;
import com.refinedmods.refinedstorage.api.network.INetworkNodeGraphEntry;
import com.refinedmods.refinedstorage.api.network.IWirelessTransmitter;
import com.refinedmods.refinedstorage.api.network.item.INetworkItem;
import com.refinedmods.refinedstorage.api.network.item.INetworkItemManager;
import com.refinedmods.refinedstorage.api.network.item.INetworkItemProvider;
import com.refinedmods.refinedstorage.api.network.node.INetworkNode;
import com.refinedmods.refinedstorage.apiimpl.network.item.NetworkItemManager;
import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.common.refinedstorage.CreativeWirelessTransmitterNetworkNode;
import me.noeffort.nuggetmod.common.refinedstorage.ICreativeNetworkNode;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(value = NetworkItemManager.class, remap = false)
public abstract class MixinNetworkItemManager implements INetworkItemManager {

    private final INetwork network;
    private final Map<PlayerEntity, INetworkItem> items = new ConcurrentHashMap<>();

    public MixinNetworkItemManager(INetwork network) {
        this.network = network;
    }

    @Overwrite
    public void open(PlayerEntity player, ItemStack stack, int slotId) {
        boolean inRange = false;
        for(INetworkNodeGraphEntry entry : this.network.getNodeGraph().all()) {
            INetworkNode node = entry.getNode();
            if(node instanceof IWirelessTransmitter &&
                    this.network.canRun() &&
                    node.isActive() &&
                    ((IWirelessTransmitter) node).getDimension().equals(player.level.dimension())) {
                IWirelessTransmitter transmitter = (IWirelessTransmitter) node;
                Vector3d pos = player.position();
                double distance = Math.sqrt(Math.pow(transmitter.getOrigin().getX() - pos.x, 2) + Math.pow(transmitter.getOrigin().getY() - pos.y, 2) + Math.pow(transmitter.getOrigin().getZ() - pos.z, 2));
                if(distance < transmitter.getRange()) {
                    inRange = true;
                    break;
                }
            } else if(node instanceof IWirelessTransmitter &&
                    this.network.canRun() &&
                    node.isActive() &&
                    !((IWirelessTransmitter) node).getDimension().equals(player.level.dimension()) &&
                    node instanceof ICreativeNetworkNode) {
                inRange = true;
                break;
            }
        }

        if(!inRange) {
            player.sendMessage(new TranslationTextComponent("misc.refinedstorage.network_item.out_of_range"), player.getUUID());
            return;
        }

        INetworkItem item = ((INetworkItemProvider) stack.getItem()).provide(this, player, stack, slotId);

        if(item.onOpen(this.network)) {
            this.items.put(player, item);
        }
    }

}
