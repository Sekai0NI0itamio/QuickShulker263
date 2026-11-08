package itamio.quickshulker;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class QuickShulkerClient {
    public static final KeyMapping OPEN_KEY = new KeyMapping(
            "key.quickshulker.open",
            net.minecraftforge.client.settings.KeyConflictContext.IN_GAME,
            com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM,
            75,
            "key.categories.quickshulker");

    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener((RegisterKeyMappingsEvent event) -> {
            event.register(OPEN_KEY);
        });
        MinecraftForge.EVENT_BUS.addListener((TickEvent.ClientTickEvent event) -> {
            if (event.phase != TickEvent.Phase.END) return;
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;
            while (OPEN_KEY.consumeClick()) {
                int slot;
                if (mc.player.getMainHandItem().isEmpty() && !mc.player.getOffhandItem().isEmpty()) {
                    slot = 40;
                } else {
                    slot = mc.player.getInventory().selected;
                }
                if (QuickShulkerForgeMod.isOpenable(mc.player.getInventory().getItem(slot))) {
                    QuickShulkerForgeMod.INSTANCE.sendToServer(new OpenShulkerPacket(slot));
                }
            }
        });
    }
}
