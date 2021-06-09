package me.noeffort.nuggetmod.util;

import me.noeffort.nuggetmod.NuggetMod;
import net.minecraft.util.text.TranslationTextComponent;

public class Format {

    public static TranslationTextComponent translate(Type type, String translate) {
        return new TranslationTextComponent(String.format("%s.%s.%s", type.getPrefix(), NuggetMod.MOD_ID, translate));
    }

    public enum Type {

        ITEM("item"),
        BLOCK("block"),
        FLUID("fluid"),
        CONTAINER("container"),
        ENCHANTMENT("enchantment"),
        ITEM_GROUP("itemGroup"),
        TOOLTIP("tooltip"),
        KEY("key"),
        MISC("misc")
        ;

        private final String prefix;

        Type(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return this.prefix;
        }
    }

}
