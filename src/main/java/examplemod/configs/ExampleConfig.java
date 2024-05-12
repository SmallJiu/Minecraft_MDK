package examplemod.configs;

import examplemod.ExampleMod;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Config(
        modid = ExampleMod.MODID,
        name = ExampleMod.MODID + "/main",
        category = "config_main")
@Mod.EventBusSubscriber(modid = ExampleMod.MODID)
public class ExampleConfig {

    @Config.LangKey("examplemod.config.example.boolean")
    @Config.Comment("a boolean config entry")
    public static boolean Boolean_Config = false;

    @Config.LangKey("examplemod.config.example.string")
    @Config.Comment("a boolean config entry")
    public static String String_Config = "default string";

    @Config.LangKey("examplemod.config.example.int")
    @Config.Comment("a int config entry")
    @Config.RangeInt(min = 0, max = 10)
    public static int Int_Config = 0;

    @Config.LangKey("examplemod.config.example.float")
    @Config.Comment("a double config entry")
    @Config.RangeDouble(min = 0.1, max = 9.9)
    public static double Double_Config = 0;

    @Config.LangKey("examplemod.config.example.enum")
    @Config.Comment("a double config entry")
    @Config.RangeDouble(min = 0.1, max = 9.9)
    public static Side Enum_Config = Side.CLIENT;

    @Config.LangKey("examplemod.config.example.side")
    @Config.Comment("a object config entry")
    public static Inside Inside = new Inside();
    public static class Inside {
        @Config.LangKey("examplemod.config.example.inside.boolean")
        @Config.Comment("a boolean config entry")
        @Config.RequiresMcRestart
        public boolean Boolean_Config = false;

        @Config.LangKey("examplemod.config.example.inside.string")
        @Config.Comment("a boolean config entry")
        @Config.RequiresWorldRestart
        public String String_Config = "default string";

        @Config.LangKey("examplemod.config.example.inside.int")
        @Config.Comment("a int config entry")
        @Config.RangeInt(min = 0, max = 10)
        public int Int_Config = 0;

        @Config.LangKey("examplemod.config.example.inside.float")
        @Config.Comment("a double config entry")
        @Config.RangeDouble(min = 0.1, max = 9.9)
        public double Double_Config = 0;

        @Config.LangKey("examplemod.config.example.inside.enum")
        @Config.Comment("a double config entry")
        @Config.RangeDouble(min = 0.1, max = 9.9)
        public Side Enum_Config = Side.CLIENT;
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if(ExampleMod.MODID.equals(event.getModID())) {
            ConfigManager.sync(ExampleMod.MODID, Config.Type.INSTANCE);
        }
    }
}
