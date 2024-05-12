package examplemod;

import examplemod.configs.ExampleConfig;
import examplemod.net.NetworkHandler;
import examplemod.ui.GuiHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(ExampleMod.MODID)
public class ExampleMod {
    public static final String MODID = "examplemod";
    public static final String NAME = "ExampleMod";
    public static final String VERSION = "1.0.0";

    public static final org.apache.logging.log4j.Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(NAME);

    public static final NetworkHandler NETWORK = NetworkHandler.getInstance();

    public ExampleMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        GuiHandler.MENU_TYPE_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ExampleConfig.CONFIG_MAIN, "examplemod.toml");
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event){

    }
    private void onClientSetup(final FMLClientSetupEvent event) {
        GuiHandler.registerScreen();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartedEvent event) {
        proxy.isServerClosed = false;
    }
    @SubscribeEvent
    public void onServerStopped(ServerStoppedEvent event) {
        proxy.isServerClosed = true;
    }

    public static class proxy {
        static boolean isServerClosed = true;

        public static Dist getSide() {
            return FMLLoader.getDist();
        }
        public static boolean isClient() {
            return getSide().isClient();
        }
        public static boolean isServerClosed() {
            return isServerClosed;
        }
    }
}
