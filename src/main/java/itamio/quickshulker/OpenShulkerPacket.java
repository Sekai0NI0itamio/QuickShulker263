package itamio.quickshulker;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class OpenShulkerPacket {
    private final int slot;

    public OpenShulkerPacket(int slot) {
        this.slot = slot;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(slot);
    }

    public static OpenShulkerPacket decode(FriendlyByteBuf buf) {
        return new OpenShulkerPacket(buf.readInt());
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> QuickShulkerForgeMod.openItem(ctx.getSender(), slot));
        ctx.setPacketHandled(true);
    }
}
