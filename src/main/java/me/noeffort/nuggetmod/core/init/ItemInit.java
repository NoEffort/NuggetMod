package me.noeffort.nuggetmod.core.init;

import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.common.item.TravelBagItem;
import me.noeffort.nuggetmod.common.item.TravelBagUpgradeItem;
import me.noeffort.nuggetmod.common.item.armor.*;
import me.noeffort.nuggetmod.common.item.curio.GodCharm;
import me.noeffort.nuggetmod.common.item.variants.ItemVariantGroup;
import me.noeffort.nuggetmod.core.itemgroup.NuggetItemGroup;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NuggetMod.MOD_ID);

    public static final RegistryObject<Item> DRUMSTICK = ITEMS.register("drumstick", () ->
            new Item(new Item.Properties().tab(NuggetItemGroup.TAB_NUGGET_MOD)
                    .food(new Food.Builder().meat().nutrition(1).saturationMod(0.F).build())));

    public static final RegistryObject<Item> COOKED_DRUMSTICK = ITEMS.register("cooked_drumstick", () ->
            new Item(new Item.Properties().tab(NuggetItemGroup.TAB_NUGGET_MOD)
                    .food(new Food.Builder().meat().nutrition(4).saturationMod(0.5F).build())));

    public static final RegistryObject<Item> UNOBTAINIUM_INGOT = ITEMS.register("unobtainium_ingot", () ->
            new Item(new Item.Properties().tab(NuggetItemGroup.TAB_NUGGET_MOD)));

    public static final RegistryObject<Item> UNOBTAINIUM_NUGGET = ITEMS.register("unobtainium_nugget", () ->
            new Item(new Item.Properties().tab(NuggetItemGroup.TAB_NUGGET_MOD)));

    public static final RegistryObject<Item> VIBRANIUM_INGOT = ITEMS.register("vibranium_ingot", () ->
            new Item(new Item.Properties().tab(NuggetItemGroup.TAB_NUGGET_MOD)));

    public static final RegistryObject<Item> VIBRANIUM_NUGGET = ITEMS.register("vibranium_nugget", () ->
            new Item(new Item.Properties().tab(NuggetItemGroup.TAB_NUGGET_MOD)));

    public static final RegistryObject<Item> ALLTHEMODIUM_INGOT = ITEMS.register("allthemodium_ingot", () ->
            new Item(new Item.Properties().tab(NuggetItemGroup.TAB_NUGGET_MOD)));

    public static final RegistryObject<Item> ALLTHEMODIUM_NUGGET = ITEMS.register("allthemodium_nugget", () ->
            new Item(new Item.Properties().tab(NuggetItemGroup.TAB_NUGGET_MOD)));

    public static final RegistryObject<Item> REINFORCED_STRING = ITEMS.register("reinforced_string", () ->
            new Item(new Item.Properties().tab(NuggetItemGroup.TAB_NUGGET_MOD)));

    public static final RegistryObject<Item> MOLTEN_VIBRANIUM_BUCKET = ITEMS.register("molten_vibranium_bucket", () ->
            new BucketItem(FluidInit.MOLTEN_VIBRANIUM, new Item.Properties().tab(NuggetItemGroup.TAB_NUGGET_MOD)
                    .stacksTo(1).craftRemainder(Items.BUCKET)));

    public static final RegistryObject<Item> UNOBTAINIUM_HELMET = ITEMS.register("unobtainium_helmet", () ->
            new UnobtainiumHelmet(ArmorMaterial.UNOBTAINIUM, new Item.Properties().tab(NuggetItemGroup.TAB_NUGGET_MOD)
                    .stacksTo(1).fireResistant()));

    public static final RegistryObject<Item> UNOBTAINIUM_CHESTPLATE = ITEMS.register("unobtainium_chestplate", () ->
            new UnobtainiumChestplate(ArmorMaterial.UNOBTAINIUM, new Item.Properties().tab(NuggetItemGroup.TAB_NUGGET_MOD)
                    .stacksTo(1).fireResistant()));

    public static final RegistryObject<Item> UNOBTAINIUM_LEGGINGS = ITEMS.register("unobtainium_leggings", () ->
            new UnobtainiumLeggings(ArmorMaterial.UNOBTAINIUM, new Item.Properties().tab(NuggetItemGroup.TAB_NUGGET_MOD)
                    .stacksTo(1).fireResistant()));

    public static final RegistryObject<Item> UNOBTAINIUM_BOOTS = ITEMS.register("unobtainium_boots", () ->
            new UnobtainiumBoots(ArmorMaterial.UNOBTAINIUM, new Item.Properties().tab(NuggetItemGroup.TAB_NUGGET_MOD)
                    .stacksTo(1).fireResistant()));

    public static final RegistryObject<Item> GOD_CHARM = ITEMS.register("god_charm", () ->
            new GodCharm(new Item.Properties().tab(NuggetItemGroup.TAB_NUGGET_MOD)));

    public static class VariantGroups {

        public static final ItemVariantGroup<TravelBagItem.Type, TravelBagItem> TRAVEL_BAG_VARIANTS =
                ItemVariantGroup.Builder.<TravelBagItem.Type, TravelBagItem>create()
                        .group("travel_bag")
                        .suffix()
                        .variants(TravelBagItem.Type.values())
                        .factory(TravelBagItem::new)
                        .properties(p -> new Item.Properties().stacksTo(1).tab(NuggetItemGroup.TAB_NUGGET_MOD))
                        .build();

        public static final ItemVariantGroup<TravelBagUpgradeItem.Type, TravelBagUpgradeItem> TRAVEL_BAG_UPGRADE_VARIANTS =
                ItemVariantGroup.Builder.<TravelBagUpgradeItem.Type, TravelBagUpgradeItem>create()
                        .group("travel_bag_upgrade")
                        .suffix()
                        .variants(TravelBagUpgradeItem.Type.values())
                        .factory(TravelBagUpgradeItem::new)
                        .properties(p -> new Item.Properties().stacksTo(1).tab(NuggetItemGroup.TAB_NUGGET_MOD))
                        .build();

    }

}
