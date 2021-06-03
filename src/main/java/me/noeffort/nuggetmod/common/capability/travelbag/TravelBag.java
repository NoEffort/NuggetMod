package me.noeffort.nuggetmod.common.capability.travelbag;

import me.noeffort.nuggetmod.common.item.TravelBagItem;
import net.minecraftforge.items.ItemStackHandler;

public class TravelBag implements ITravelBag {

    private ItemStackHandler handler;
    private TravelBagItem.Type type;

    public TravelBag() {
        this.type = TravelBagItem.Type.DEFAULT;
        this.handler = new ItemStackHandler(TravelBagItem.getSize(this.type));
    }

    @Override
    public ItemStackHandler getHandler() {
        return this.handler;
    }

    @Override
    public void setHandler(ItemStackHandler handler) {
        this.handler = handler;
    }

    @Override
    public TravelBagItem.Type getType() {
        return this.type;
    }

    @Override
    public void setType(TravelBagItem.Type type) {
        this.type = type;
    }

}
