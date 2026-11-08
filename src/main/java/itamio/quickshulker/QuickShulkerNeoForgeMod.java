package itamio.quickshulker;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
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
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Unofficial NeoForge port of QuickShulker by kyrptonaught (MIT).
 * See NOTICE for credit and licensing.
 */
@Mod("quickshulker")
public class QuickShulkerNeoForgeMod {
    public static final KeyMapping OPEN_KEY = new KeyMapping(
        "key.quickshulker.open",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYBOARD,
        75,
        "key.categories.quickshulker");

    public QuickShulkerNeoForgeMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::registerPayloads);
        modEventBus.addListener(this::registerKeyMappings);
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.addListener(this::onClientTick);
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar("1").playToServer(OpenShulkerPayload.ID, OpenShulkerPayload.STREAM_CODEC,
            (payload, context) -> context.enqueueWork(() -> {
                if (context.player() != null) {
                    openItem(context.player(), payload.slot());
                }
            }));
    }

    private void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_KEY);
    }

    private void onClientTick(PlayerTickEvent.Post event) {
        if (!(event.entity instanceof net.minecraft.world.entity.player.Player player)) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != player) {
            return;
        }
        while (OPEN_KEY.consumeClick()) {
            int slot;
            if (mc.player.getMainHandItem().isEmpty() && !mc.player.getOffhandItem().isEmpty()) {
                slot = 40;
            } else {
                slot = mc.player.getInventory().getSelectedSlot();
            }
            if (isOpenable(mc.player.getInventory().getItem(slot))) {
                PacketDistributor.sendToServer(new OpenShulkerPayload(slot));
            }
        }
    }

    public static boolean isOpenable(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof BlockItem blockItem)) return false;
        if (blockItem.getBlock() instanceof ShulkerBoxBlock) return stack.getCount() <= 1;
        return blockItem.getBlock() instanceof EnderChestBlock
            || blockItem.getBlock() instanceof CraftingTableBlock
            || blockItem.getBlock() instanceof StonecutterBlock;
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
