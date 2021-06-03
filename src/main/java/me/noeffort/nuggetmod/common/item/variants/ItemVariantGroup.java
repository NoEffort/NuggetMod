package me.noeffort.nuggetmod.common.item.variants;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import me.noeffort.nuggetmod.core.itemgroup.NuggetItemGroup;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public class ItemVariantGroup<V extends Enum<V> & IStringSerializable, K extends Item> implements IItemVariantGroup<V, K> {

    private final String group;
    private final boolean isSuffix;
    private final Iterable<V> variants;

    private final Function<V, Item.Properties> properties;
    private final ItemFactory<V, K> factory;

    private Map<V, K> items;

    private ItemVariantGroup(final String group, final boolean isSuffix, final Iterable<V> variants,
                             final Function<V, Item.Properties> properties, final ItemFactory<V, K> factory) {
        this.group = group;
        this.isSuffix = isSuffix;
        this.variants = variants;
        this.properties = properties;
        this.factory = factory;
    }

    @Override
    public String getGroupName() {
        return this.group;
    }

    @Override
    public Iterable<V> getVariants() {
        return this.variants;
    }

    @Override
    public Collection<K> getItems() {
        return this.getItemsMap().values();
    }

    public Map<V, K> getItemsMap() {
        return this.items;
    }

    public K getItem(final V variant) {
        return this.items.get(variant);
    }

    @Override
    public void register(IForgeRegistry<Item> registry) {
        Preconditions.checkState(this.items == null,
                "Attempt to re-register items for Variant Group %s", this.group);

        final ImmutableMap.Builder<V, K> builder = ImmutableMap.builder();
        this.getVariants().forEach(variant -> {
            final String name = (this.isSuffix)
                    ? this.group + "_" + variant.getSerializedName()
                    : variant.getSerializedName() + "_" + this.group;

            final Item.Properties properties = this.properties.apply(variant);
            final K item = this.factory.create(variant, this, properties);

            item.setRegistryName(name);

            registry.register(item);
            builder.put(variant, item);
        });

        this.items = builder.build();
    }

    @FunctionalInterface
    public interface ItemFactory<V extends Enum<V> & IStringSerializable, K extends Item> {
        K create(V variant, ItemVariantGroup<V, K> group, Item.Properties properties);
    }

    public static class Builder<V extends Enum<V> & IStringSerializable, K extends Item> {

        private String group;
        private boolean isSuffix;
        private Iterable<V> variants;

        private Function<V, Item.Properties> properties = variant ->
                new Item.Properties().tab(NuggetItemGroup.TAB_NUGGET_MOD);
        private ItemFactory<V, K> factory;

        public static <V extends Enum<V> & IStringSerializable, K extends Item> ItemVariantGroup.Builder<V, K> create() {
            return new ItemVariantGroup.Builder<>();
        }

        private Builder() {}

        public ItemVariantGroup.Builder<V, K> group(@Nonnull final String group) {
            this.group = group;
            return this;
        }

        public ItemVariantGroup.Builder<V, K> suffix() {
            this.isSuffix = true;
            return this;
        }

        public ItemVariantGroup.Builder<V, K> variants(@Nonnull final Iterable<V> variants) {
            this.variants = variants;
            return this;
        }

        public ItemVariantGroup.Builder<V, K> variants(@Nonnull final V[] variants) {
            return this.variants(Arrays.asList(variants));
        }

        public ItemVariantGroup.Builder<V, K> properties(@Nonnull final Function<V, Item.Properties> properties) {
            this.properties = properties;
            return this;
        }

        public ItemVariantGroup.Builder<V, K> factory(@Nonnull final ItemFactory<V, K> factory) {
            this.factory = factory;
            return this;
        }

        public ItemVariantGroup<V, K> build() {
            Preconditions.checkState(this.group != null, "Group Name not provided!");
            Preconditions.checkState(this.variants != null, "Variants not provided!");
            Preconditions.checkState(this.factory != null, "Item Factory not provided!");
            return new ItemVariantGroup<>(this.group, this.isSuffix, this.variants, this.properties, this.factory);
        }

    }

}
