package example.net;

import cat.jiu.core.net.BaseNetworkHandler;
import example.ExampleModMain;
import net.minecraft.resources.ResourceLocation;

public class ExampleNetwork extends BaseNetworkHandler {
    private static ExampleNetwork INSTANCE;

    public static ExampleNetwork getInstance() {
        if (INSTANCE==null) INSTANCE = new ExampleNetwork();
        return INSTANCE;
    }

    private ExampleNetwork() {
        super(new ResourceLocation(ExampleModMain.MODID, "main_network"), ExampleModMain.VERSION);

//		this.register(Msg.class, NetworkDirection.PLAY_TO_SERVER, NetworkDirection.PLAY_TO_CLIENT)
//			.register(Msg2.class, NetworkDirection.PLAY_TO_SERVER);
//			.register(Msg3.class, NetworkDirection.PLAY_TO_CLIENT);
    }
}
