package itamio.quickshulker;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record OpenShulkerPayload(int slot) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OpenShulkerPayload> ID =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath("quickshulker263", "open_shulker"));
    public static final StreamCodec<FriendlyByteBuf, OpenShulkerPayload> CODEC =
            ByteBufCodecs.INT.map(OpenShulkerPayload::new, OpenShulkerPayload::slot);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
