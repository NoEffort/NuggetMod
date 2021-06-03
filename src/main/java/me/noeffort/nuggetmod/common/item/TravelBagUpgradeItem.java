package me.noeffort.nuggetmod.common.item;

import me.noeffort.nuggetmod.common.item.variants.ItemVariantGroup;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public class TravelBagUpgradeItem extends Item {

    private final TravelBagUpgradeItem.Type type;
    private final ItemVariantGroup<TravelBagUpgradeItem.Type, TravelBagUpgradeItem> group;

    public TravelBagUpgradeItem(final TravelBagUpgradeItem.Type type, final ItemVariantGroup<TravelBagUpgradeItem.Type,
            TravelBagUpgradeItem> group, final Item.Properties properties) {
        super(properties);
        this.type = type;
        this.group = group;
    }

    public TravelBagUpgradeItem.Type getType() {
        return this.type;
    }

    public ItemVariantGroup<TravelBagUpgradeItem.Type, TravelBagUpgradeItem> getVariantGroup() {
        return this.group;
    }

    public enum Type implements IStringSerializable {

        IRON("iron", TravelBagItem.Type.DEFAULT, TravelBagItem.Type.IRON),
        GOLD("gold", TravelBagItem.Type.IRON, TravelBagItem.Type.GOLD),
        DIAMOND("diamond", TravelBagItem.Type.GOLD, TravelBagItem.Type.DIAMOND),
        ;

        private final String name;
        private final TravelBagItem.Type from;
        private final TravelBagItem.Type to;

        Type(final String name, TravelBagItem.Type from, TravelBagItem.Type to) {
            this.name = name;
            this.from = from;
            this.to = to;
        }

        @Nonnull
        @Override
        public String getSerializedName() {
            return this.name;
        }

        public TravelBagItem.Type getFrom() {
            return this.from;
        }

        public TravelBagItem.Type getTo() {
            return this.to;
        }

    }

}
