package examplemod.configs;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.network.NetworkDirection;

public class ExampleConfig {
    public static final ForgeConfigSpec.BooleanValue Boolean_Config;
    public static final ForgeConfigSpec.ConfigValue<String> String_Config;
    public static final ForgeConfigSpec.IntValue Int_Config;
    public static final ForgeConfigSpec.DoubleValue Double_Config;
    public static final ForgeConfigSpec.EnumValue<NetworkDirection> Enum_Config;
    public static final Inside Inside;

    public static class Inside extends BaseConfig {
        public final ForgeConfigSpec.BooleanValue Boolean_Config;
        public final ForgeConfigSpec.ConfigValue<String> String_Config;
        public final ForgeConfigSpec.IntValue Int_Config;
        public final ForgeConfigSpec.DoubleValue Double_Config;
        public final ForgeConfigSpec.EnumValue<NetworkDirection> Enum_Config;
        public Inside(ForgeConfigSpec.Builder builder) {
            super(builder);
            builder.comment("Inside settings").push("inside");

            Boolean_Config = builder.translation("examplemod.config.example.inside.boolean")
                    .comment("a boolean config entry")
                    .define("Boolean_Config", false);

            String_Config = builder.translation("examplemod.config.example.inside.string")
                    .comment("a string config entry")
                    .define("String_Config", "default string");

            Int_Config = builder.translation("examplemod.config.example.inside.int")
                    .comment("a boolean config entry")
                    .defineInRange("Int_Config", 0, 0, 999);

            Double_Config = builder.translation("examplemod.config.example.inside.double")
                    .comment("a double config entry")
                    .defineInRange("Double_Config", 0,0, 0.99);

            Enum_Config = builder.translation("examplemod.config.example.inside.enum")
                    .comment("a enum config entry")
                    .defineEnum("Enum_Config", NetworkDirection.PLAY_TO_SERVER);

            builder.pop();
        }
    }


    public static final ForgeConfigSpec CONFIG_MAIN;
    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        Boolean_Config = builder.translation("examplemod.config.example.boolean")
                .comment("a boolean config entry")
                .define("Boolean_Config", false);

        String_Config = builder.translation("examplemod.config.example.string")
                .comment("a string config entry")
                .define("String_Config", "default string");

        Int_Config = builder.translation("examplemod.config.example.int")
                .comment("a boolean config entry")
                .defineInRange("Int_Config", 0, 0, 999);

        Double_Config = builder.translation("examplemod.config.example.double")
                .comment("a double config entry")
                .defineInRange("Double_Config", 0,0, 0.99);

        Enum_Config = builder.translation("examplemod.config.example.enum")
                .comment("a enum config entry")
                .defineEnum("Enum_Config", NetworkDirection.PLAY_TO_SERVER);

        Inside = new Inside(builder);

        CONFIG_MAIN = builder.build();
    }

    public static class BaseConfig {
        public BaseConfig(@SuppressWarnings("unused") ForgeConfigSpec.Builder builder) {}
    }
}
