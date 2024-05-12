package examplemod;

import examplemod.net.NetworkHandler;
import examplemod.proxy.ServerProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;

@Mod(
        modid = ExampleMod.MODID,
        name = ExampleMod.NAME,
        version = ExampleMod.VERSION,
        useMetadata = true,
        guiFactory = "examplemod.configs.ConfigGuiFactory"
)
public class ExampleMod {
    public static final String MODID = "examplemod";
    public static final String NAME = "ExampleMod";
    public static final String VERSION = "1.0.0";

    public static final org.apache.logging.log4j.Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(NAME);

    public static final NetworkHandler NETWORK = NetworkHandler.getInstance();

    @SidedProxy(
            serverSide = "examplemod.proxy.ServerProxy",
            clientSide = "examplemod.proxy.ClientProxy",
            modId = ExampleMod.MODID
    )
    public static ServerProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event) {

    }
    @Mod.EventHandler
    public void onServerClosed(FMLServerStoppedEvent event) {

    }
}
