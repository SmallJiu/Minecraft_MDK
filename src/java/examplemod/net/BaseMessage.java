package examplemod.net;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class BaseMessage {
    public abstract void toBytes(PacketBuffer buffer);
    public abstract void fromBytes(PacketBuffer buf);
    public abstract boolean handler(Supplier<NetworkEvent.Context> context);
}
