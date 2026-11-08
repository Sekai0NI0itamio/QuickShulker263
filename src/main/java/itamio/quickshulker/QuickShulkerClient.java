package itamio.quickshulker;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class QuickShulkerClient implements ClientModInitializer {
    public static final KeyMapping OPEN_KEY = new KeyMapping(
            "key.quickshulker263.open",
            InputConstants.Type.KEYSYM,
            75,
            "QuickShulker");

    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(OPEN_KEY);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;
            while (OPEN_KEY.consumeClick()) {
                int slot;
                if (mc.player.getMainHandItem().isEmpty() && !mc.player.getOffhandItem().isEmpty()) {
                    slot = 40;
                } else {
                    slot = mc.player.getInventory().selected;
                }
                if (QuickShulkerMod.isOpenable(mc.player.getInventory().getItem(slot))) {
                    ClientPlayNetworking.send(new OpenShulkerPayload(slot));
                }
            }
        });
    }
}
