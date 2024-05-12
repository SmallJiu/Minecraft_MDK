package examplemod.net;

import examplemod.ExampleMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.HashMap;
import java.util.HashSet;

public class NetworkHandler {
	private static NetworkHandler INSTANCE;

	public static NetworkHandler getInstance() {
		if (INSTANCE == null) INSTANCE = new NetworkHandler();
		return INSTANCE;
	}

	private final SimpleChannel channel;
	
	private static int ID = 0;
	private static int nextID() {
		return ID++;
	}
	
	private NetworkHandler() {
		ID = 0;
		this.channel = NetworkRegistry.newSimpleChannel(
				new ResourceLocation(ExampleMod.MODID, "main_network"),
				ExampleMod.VERSION::toString,
				ExampleMod.VERSION::equals,
				ExampleMod.VERSION::equals
		);

//		this.register(Msg.class, NetworkDirection.PLAY_TO_SERVER, NetworkDirection.PLAY_TO_CLIENT)
//			.register(Msg2.class, NetworkDirection.PLAY_TO_SERVER);
//			.register(Msg3.class, NetworkDirection.PLAY_TO_CLIENT);
	}

	private static final HashMap<Class<? extends BaseMessage>, HashSet<NetworkDirection>> REGISTRY = new HashMap<>();
	private <T extends BaseMessage> NetworkHandler register(Class<T> msgClass, NetworkDirection... sendTo) {
		for (NetworkDirection side : sendTo) {
			if (!REGISTRY.containsKey(msgClass) || !REGISTRY.get(msgClass).contains(side)) {
				this.channel.messageBuilder(msgClass, nextID(), side)
						.encoder(T::toBytes)
						.decoder(buf ->{
							try {
								T instance = msgClass.getDeclaredConstructor().newInstance();
								instance.fromBytes(buf);
								return instance;
							} catch (Exception e) {
								return null;
							}
						})
						.consumerNetworkThread(T::handler)
						.add();
				if (!REGISTRY.containsKey(msgClass)) {
					REGISTRY.put(msgClass, new HashSet<>());
				}
				REGISTRY.get(msgClass).add(side);
			}
		}
		return this;
	}

	/** server to client */
	public void sendMessageToPlayer(cat.jiu.core.api.BaseMessage msg, ServerPlayer player) {
		if(msg!=null && player!=null)
			channel.send(PacketDistributor.PLAYER.with(()->player), msg);
	}

	/** client to server */
	public void sendMessageToServer(BaseMessage msg) {
		if(msg!=null) channel.sendToServer(msg);
	}
}
