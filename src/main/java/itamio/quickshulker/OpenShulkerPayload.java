package itamio.quickshulker;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record OpenShulkerPayload(int slot) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OpenShulkerPayload> ID =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("quickshulker263", "open_shulker"));
    public static final StreamCodec<FriendlyByteBuf, OpenShulkerPayload> CODEC =
            StreamCodec.of((buf, payload) -> buf.writeInt(payload.slot()),
                    buf -> new OpenShulkerPayload(buf.readInt()));

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
