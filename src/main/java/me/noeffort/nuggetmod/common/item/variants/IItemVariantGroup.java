package me.noeffort.nuggetmod.common.item.variants;

import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collection;
import java.util.Iterator;

public interface IItemVariantGroup<V extends Enum<V> & IStringSerializable, K extends Item> {

    String getGroupName();

    default V cycle(final V current) {
        final Iterator<V> iterator = this.getVariants().iterator();
        while(iterator.hasNext()) {
            if(iterator.next().equals(current)) {
                return (iterator.hasNext()) ? iterator.next() : this.getVariants().iterator().next();
            }
        }
        return iterator.next();
    }

    Iterable<V> getVariants();
    Collection<K> getItems();
    void register(IForgeRegistry<Item> registry);

}
