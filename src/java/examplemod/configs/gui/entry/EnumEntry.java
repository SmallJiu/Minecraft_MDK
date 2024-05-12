package examplemod.configs.gui.entry;

import com.mojang.blaze3d.matrix.MatrixStack;
import examplemod.configs.gui.ConfigEntry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraftforge.common.ForgeConfigSpec;

public class EnumEntry<T extends Enum<T>> extends ConfigEntry<T> {
    private T cache;
    public EnumEntry(ForgeConfigSpec.EnumValue<T> value, ForgeConfigSpec.ValueSpec spec) {
        super(value, spec);
    }

    @Override
    public void render(Screen gui, MatrixStack matrix, int x, int y, int mouseX, int mouseY) {

    }

    @Override
    protected T getCacheValue() {
        return this.cache;
    }

    @Override
    protected void setCacheValue(T newValue) {
        this.cache = newValue;
    }

    @Override
    protected Widget getConfigWidget() {
        return null;
    }
}
