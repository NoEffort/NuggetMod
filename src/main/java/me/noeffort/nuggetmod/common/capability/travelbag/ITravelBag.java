package me.noeffort.nuggetmod.common.capability.travelbag;

import me.noeffort.nuggetmod.common.item.TravelBagItem;
import net.minecraftforge.items.ItemStackHandler;

public interface ITravelBag {

    ItemStackHandler getHandler();
    void setHandler(ItemStackHandler handler);

    TravelBagItem.Type getType();
    void setType(TravelBagItem.Type type);

}
