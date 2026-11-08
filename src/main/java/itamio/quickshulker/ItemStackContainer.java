package itamio.quickshulker;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class ItemStackContainer extends SimpleContainer {
    private final ItemStack stack;
    private boolean loading = true;

    public ItemStackContainer(ItemStack stack, int size) {
        super(size);
        this.stack = stack;
        CompoundTag blockEntityTag = stack.getTagElement("BlockEntityTag");
        if (blockEntityTag != null) {
            NonNullList<ItemStack> list = NonNullList.withSize(size, ItemStack.EMPTY);
            for (Tag element : blockEntityTag.getList("Items", 10)) {
                if (element instanceof CompoundTag compound) {
                    int slot = compound.getInt("Slot");
                    if (slot >= 0 && slot < size) {
                        list.set(slot, ItemStack.of(compound));
                    }
                }
            }
            for (int i = 0; i < size; i++) {
                super.setItem(i, list.get(i));
            }
        }
        loading = false;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (!loading) {
            CompoundTag blockEntityTag = stack.getTagElement("BlockEntityTag");
            if (blockEntityTag == null) {
                blockEntityTag = new CompoundTag();
                stack.getOrCreateTag().put("BlockEntityTag", blockEntityTag);
            }
            net.minecraft.nbt.ListTag items = new net.minecraft.nbt.ListTag();
            for (int i = 0; i < getContainerSize(); i++) {
                ItemStack item = getItem(i);
                if (!item.isEmpty()) {
                    CompoundTag tag = item.save(new CompoundTag());
                    tag.putByte("Slot", (byte) i);
                    items.add(tag);
                }
            }
            blockEntityTag.put("Items", items);
        }
    }
}
