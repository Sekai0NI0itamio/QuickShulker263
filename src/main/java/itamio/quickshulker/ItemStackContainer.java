package itamio.quickshulker;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class ItemStackContainer extends SimpleContainer {
    private final ItemStack stack;
    private boolean loading = true;

    public ItemStackContainer(ItemStack stack, int size) {
        super(size);
        this.stack = stack;
        CompoundTag be = stack.getTagElement("BlockEntityTag");
        if (be != null && be.contains("Items", Tag.TAG_LIST)) {
            ListTag list = be.getList("Items", Tag.TAG_COMPOUND);
            NonNullList<ItemStack> items = NonNullList.withSize(size, ItemStack.EMPTY);
            ContainerHelper.loadAllItems(list, items);
            for (int i = 0; i < size; i++) {
                super.setItem(i, items.get(i));
            }
        }
        loading = false;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (!loading) {
            CompoundTag tag = stack.getOrCreateTag();
            CompoundTag be = tag.contains("BlockEntityTag", Tag.TAG_COMPOUND)
                    ? tag.getCompound("BlockEntityTag") : new CompoundTag();
            ContainerHelper.saveAllItems(be, getItems());
            tag.put("BlockEntityTag", be);
            stack.setTag(tag);
        }
    }
}
