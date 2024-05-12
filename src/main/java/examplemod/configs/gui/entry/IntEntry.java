package examplemod.configs.gui.entry;

import net.minecraftforge.common.ForgeConfigSpec;

public class IntEntry extends NumberEntry<Integer> {
    public IntEntry(ForgeConfigSpec.ConfigValue<Integer> value, ForgeConfigSpec.ValueSpec spec) {
        super(value, spec, false);
    }

    @Override
    protected Integer parse(String value) {
        return Integer.parseInt(value);
    }
}
