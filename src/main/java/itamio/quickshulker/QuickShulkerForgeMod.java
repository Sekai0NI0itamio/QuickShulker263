package itamio.quickshulker;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * Unofficial Forge port of QuickShulker by kyrptonaught (MIT).
 * See NOTICE for credit and licensing.
 */
@Mod("quickshulker")
public class QuickShulkerForgeMod {
    public static final String MOD_ID = "quickshulker";
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("quickshulker", "open"),
            () -> PROTOCOL_VERSION,
            version -> version.equals(PROTOCOL_VERSION),
            version -> version.equals(PROTOCOL_VERSION));

    private static int packetId = 0;

    private static int nextPacketId() {
        return packetId++;
    }

    public QuickShulkerForgeMod() {
        INSTANCE.registerMessage(nextPacketId(), OpenShulkerPacket.class,
                OpenShulkerPacket::encode,
                OpenShulkerPacket::decode,
                OpenShulkerPacket::handle);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            QuickShulkerClient.init();
        }
    }

    public static boolean isOpenable(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof BlockItem blockItem)) return false;
        if (blockItem.getBlock() instanceof ShulkerBoxBlock) return stack.getCount() <= 1;
        return blockItem.getBlock() instanceof EnderChestBlock
                || blockItem.getBlock() instanceof CraftingTableBlock
                || blockItem.getBlock() instanceof StonecutterBlock;
    }

    public static boolean canOpenInHand(ItemStack stack) {
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
            Component title = stack.hasCustomHoverName() ? stack.getHoverName() : Component.translatable("container.shulkerBox");
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
