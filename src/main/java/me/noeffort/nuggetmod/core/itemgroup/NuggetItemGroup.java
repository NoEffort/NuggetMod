package me.noeffort.nuggetmod.core.itemgroup;

import me.noeffort.nuggetmod.core.init.ItemInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class NuggetItemGroup extends ItemGroup {

    public static final NuggetItemGroup TAB_NUGGET_MOD = new NuggetItemGroup();

    public NuggetItemGroup() {
        super(ItemGroup.getGroupCountSafe(), "tab_nuggetmod");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemInit.COOKED_DRUMSTICK.get());
    }

}
