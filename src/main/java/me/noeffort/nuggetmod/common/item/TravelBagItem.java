package me.noeffort.nuggetmod.common.item;

import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.common.capability.travelbag.ITravelBag;
import me.noeffort.nuggetmod.common.capability.travelbag.TravelBag;
import me.noeffort.nuggetmod.common.capability.travelbag.TravelBagCapability;
import me.noeffort.nuggetmod.common.container.TravelBagContainer;
import me.noeffort.nuggetmod.common.item.variants.ItemVariantGroup;
import me.noeffort.nuggetmod.core.init.ItemInit;
import me.noeffort.nuggetmod.core.network.TravelBagSyncMessage;
import me.noeffort.nuggetmod.util.Format;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.logging.log4j.core.jmx.Server;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class TravelBagItem extends Item {

    private final Type type;
    private final ItemVariantGroup<Type, TravelBagItem> group;

    public TravelBagItem(final Type type, final ItemVariantGroup<Type, TravelBagItem> group, final Item.Properties properties) {
        super(properties);
        this.type = type;
        this.group = group;
    }

    public Type getType() {
        return this.type;
    }

    public ItemVariantGroup<Type, TravelBagItem> getVariantGroup() {
        return this.group;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        ITravelBag bag = new TravelBag();
        bag.setType(this.type);
        bag.setHandler(new ItemStackHandler(this.type.getSize()));
        return (TravelBagCapability.CAPABILITY_TRAVEL_BAG == null) ? null
                : TravelBagCapability.createProvider(bag);
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> use(World world, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
        if(!world.isClientSide()) {
            this.openGui(player, player.getItemInHand(hand), hand);
        }
        return ActionResult.success(player.getItemInHand(hand));
    }

    public static void sync(ItemStack item, ServerPlayerEntity player) {
        NuggetMod.CHANNEL.sendTo(new TravelBagSyncMessage(item.serializeNBT()), player.connection.getConnection(),
                NetworkDirection.PLAY_TO_CLIENT);
    }

    public static ItemStack findInCurios(Type type, PlayerEntity player) {
        TravelBagItem item = ItemInit.VariantGroups.TRAVEL_BAG_VARIANTS.getItem(type);
        Optional<ImmutableTriple<String, Integer, ItemStack>> curios = CuriosApi.getCuriosHelper()
                .findEquippedCurio(item, player);
        return curios.isPresent() ? curios.get().right : ItemStack.EMPTY;
    }

    public void openGui(PlayerEntity player, ItemStack item, Hand hand) {
        NetworkHooks.openGui((ServerPlayerEntity) player, new ContainerProvider(item, hand), buf -> {
            buf.writeEnum(hand);
        });
    }

    public static int getSize(final Type type) {
        return type.getSize();
    }

    private static class ContainerProvider implements INamedContainerProvider {

        private final IItemHandler handler;
        private final Type type;
        private final Hand hand;

        public ContainerProvider(ItemStack item, Hand hand) {
            this.hand = hand;

            ITravelBag capability = item.getCapability(TravelBagCapability.CAPABILITY_TRAVEL_BAG)
                    .orElseThrow(NullPointerException::new);

            this.handler = capability.getHandler();
            this.type = capability.getType();
        }

        @Override
        @Nonnull
        public ITextComponent getDisplayName() {
            return Format.translate(Format.Type.CONTAINER, "travel_bag");
        }

        @Nullable
        @Override
        public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
            return new TravelBagContainer(id, this.type, inventory, (IItemHandlerModifiable) this.handler, this.hand);
        }

    }

    public enum Type implements IStringSerializable {

        DEFAULT("default", 3, 165, 175),
        IRON("iron", 4, 183, 175),
        GOLD("gold", 5, 201, 175),
        DIAMOND("diamond", 6, 219, 175)
        ;

        private final String name;
        private final int rows;
        private final int imageHeight;
        private final int imageWidth;

        Type(final String name, final int rows, int imageHeight, int imageWidth) {
            this.name = name;
            this.rows = rows;
            this.imageHeight = imageHeight;
            this.imageWidth = imageWidth;
        }

        @Nonnull
        @Override
        public String getSerializedName() {
            return this.name;
        }

        public int getRows() {
            return this.rows;
        }

        public int getImageHeight() {
            return this.imageHeight;
        }

        public int getImageWidth() {
            return this.imageWidth;
        }

        public int getSize() {
            return this.rows * 9;
        }

    }

}
