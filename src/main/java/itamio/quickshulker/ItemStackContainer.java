package itamio.quickshulker;

import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public class ItemStackContainer extends SimpleContainer {
    private final ItemStack stack;
    private boolean loading = true;

    public ItemStackContainer(ItemStack stack, int size) {
        super(size);
        this.stack = stack;
        ItemContainerContents contents = stack.get(DataComponents.CONTAINER);
        if (contents != null) {
            NonNullList<ItemStack> list = NonNullList.withSize(size, ItemStack.EMPTY);
            contents.copyInto(list);
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
            stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(getItems()));
        }
    }
}
