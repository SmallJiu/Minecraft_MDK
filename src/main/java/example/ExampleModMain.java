package example;

import example.configs.ExampleConfigs;
import example.net.ExampleNetwork;
import example.ui.GuiHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ExampleModMain.MODID)
public class ExampleModMain {
    public static final String MODID = "examplemod";
    public static final String NAME = "ExampleMod";
    public static final String VERSION = "1.20.1-1.0.0";

    public static final org.apache.logging.log4j.Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(NAME);

    public static final ExampleNetwork NETWORK = ExampleNetwork.getInstance();

    public ExampleModMain() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        GuiHandler.MENU_TYPE_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ExampleConfigs.CONFIG_MAIN, "example.toml");
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event){

    }
    private void onClientSetup(final FMLClientSetupEvent event) {
        GuiHandler.registerScreen();
    }
}
