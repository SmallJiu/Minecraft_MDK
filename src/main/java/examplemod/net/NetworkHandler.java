package examplemod.net;

import examplemod.ExampleMod;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
	private static NetworkHandler INSTANCE;

	public static NetworkHandler getInstance() {
		if (INSTANCE == null) INSTANCE = new NetworkHandler();
		return INSTANCE;
	}

	private final SimpleNetworkWrapper channel;
	
	private static int ID = 0;
	private static int nextID() {
		return ID++;
	}
	
	private NetworkHandler() {
		ID = 0; 
		this.channel = NetworkRegistry.INSTANCE.newSimpleChannel(ExampleMod.MODID);

//		this.register(Msg.class, Side.CLIENT, Side.SERVER)
//			.register(Msg2.class, Side.CLIENT);
//			.register(Msg3.class, Side.SERVER);
	}
	
	private <T extends BaseMessage> NetworkHandler register(Class<T> msgClass, Side... sendTo) {
		for (Side side : sendTo) {
			this.channel.registerMessage(T::handler, msgClass, nextID(), side);
		}
		return this;
	}
	
	/** server to client */
	public void sendMessageToPlayer(IMessage msg, EntityPlayerMP player) {
		if(msg!=null && player!=null) channel.sendTo(msg, player);
	}
	
	/** client to server */
	public void sendMessageToServer(IMessage msg) {
		if(msg!=null) channel.sendToServer(msg);
	}
}
