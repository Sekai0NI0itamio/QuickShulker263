package itamio.quickshulker;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class OpenShulkerPayload {
    public static final ResourceLocation ID =
            new ResourceLocation("quickshulker263", "open_shulker");

    @Environment(EnvType.CLIENT)
    public static void send(int slot) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeInt(slot);
        ClientPlayNetworking.send(ID, buf);
    }
}
