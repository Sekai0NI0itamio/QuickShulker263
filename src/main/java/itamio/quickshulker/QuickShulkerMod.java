package itamio.quickshulker;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.inventory.StonecutterMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.EnderChestBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.StonecutterBlock;

public class QuickShulkerMod implements ModInitializer {
    public static final String MOD_ID = "quickshulker263";

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.playC2S().register(OpenShulkerPayload.ID, OpenShulkerPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(OpenShulkerPayload.ID, (payload, context) -> {
            context.server().execute(() -> openItem(context.player(), payload.slot()));
        });

    }

    public static boolean isOpenable(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof BlockItem blockItem)) return false;
        if (blockItem.getBlock() instanceof ShulkerBoxBlock) return stack.getCount() <= 1;
        return blockItem.getBlock() instanceof EnderChestBlock
                || blockItem.getBlock() instanceof CraftingTableBlock
                || blockItem.getBlock() instanceof StonecutterBlock;
    }

    public static boolean canOpenInHand(ItemStack stack) {
        // Original allowed all registered block types to open in hand.
        return isOpenable(stack);
    }

    public static void openItem(Player player, int invSlot) {
        if (player == null || invSlot < 0) return;
        ItemStack stack;
        try {
            stack = player.getInventory().getItem(invSlot);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        if (!isOpenable(stack)) return;
        if (!(stack.getItem() instanceof BlockItem blockItem)) return;

        if (blockItem.getBlock() instanceof ShulkerBoxBlock) {
            Component title = stack.getCustomName() != null ? stack.getHoverName() : Component.translatable("container.shulkerBox");
            player.openMenu(new SimpleMenuProvider(
                    (id, inv, p) -> new ShulkerBoxMenu(id, inv, new ItemStackContainer(stack, 27)), title));
        } else if (blockItem.getBlock() instanceof EnderChestBlock) {
            player.openMenu(new SimpleMenuProvider(
                    (id, inv, p) -> ChestMenu.threeRows(id, inv, player.getEnderChestInventory()),
                    Component.translatable("container.enderchest")));
        } else if (blockItem.getBlock() instanceof CraftingTableBlock) {
            player.openMenu(new SimpleMenuProvider(
                    (id, inv, p) -> new CraftingMenu(id, inv, ContainerLevelAccess.NULL),
                    Component.translatable("container.crafting")));
        } else if (blockItem.getBlock() instanceof StonecutterBlock) {
            player.openMenu(new SimpleMenuProvider(
                    (id, inv, p) -> new StonecutterMenu(id, inv, ContainerLevelAccess.NULL),
                    Component.translatable("container.stonecutter")));
        }
    }
}
