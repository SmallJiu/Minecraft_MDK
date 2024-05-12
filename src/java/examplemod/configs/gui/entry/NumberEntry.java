package examplemod.configs.gui.entry;

import com.mojang.blaze3d.matrix.MatrixStack;
import examplemod.configs.gui.ConfigEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import java.awt.*;
import java.util.function.Predicate;

public abstract class NumberEntry<T extends Number> extends ConfigEntry<T> {
    protected final GuiFilterTextField field;
    protected final boolean isDecimal;
    protected T cache;
    protected NumberEntry(ForgeConfigSpec.ConfigValue<T> value, ForgeConfigSpec.ValueSpec spec, boolean isDecimal) {
        super(value, spec);
        this.isDecimal = isDecimal;
        this.field = this.addWidget(new GuiFilterTextField(String.valueOf(this.cache), Minecraft.getInstance().fontRenderer, 0,0,150, 18).setTypedCharFilter(typedChar ->
                (isDecimal ? "0123456789." : "0123456789").contains(String.valueOf(typedChar))));
        this.field.x = Minecraft.getInstance().getMainWindow().getScaledWidth()/2 - this.field.getWidth()/2 + this.field.getWidth() - this.field.getWidth()/2 - 1;
        this.addUndoAndReset();
    }

    @Override
    protected Widget getConfigWidget() {
        return this.field;
    }

    @Override
    public void render(Screen gui, MatrixStack matrix, int x, int y, int mouseX, int mouseY) {
        this.renderWidget(gui, matrix, x, y, mouseX, mouseY);
        this.drawAlignRightString(matrix, this.configName, this.field.x - 5, this.field.y + 5, Color.WHITE.getRGB(), true, gui.getMinecraft().fontRenderer);
    }

    @Override
    public void drawHoverText(Screen gui, MatrixStack matrix, int mouseX, int mouseY) {
        try {
            this.drawCommentWithRange(gui, matrix, mouseX, mouseY,
                    this.field.x-5-gui.getMinecraft().fontRenderer.getStringWidth(this.configName), this.field.y+5,
                    gui.getMinecraft().fontRenderer.getStringWidth(this.configName), gui.getMinecraft().fontRenderer.FONT_HEIGHT);
        } catch (Exception e) {e.printStackTrace();}
    }

    protected abstract T parse(String value);

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        boolean flag = super.charTyped(codePoint, modifiers);
        this.setCacheValue(this.parse(this.field.getText()));
        return flag;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean flag = super.keyPressed(keyCode, scanCode, modifiers);
        this.setCacheValue(this.parse(this.field.getText()));
        return flag;
    }

    @Override
    protected T getCacheValue() {
        return this.cache;
    }

    @Override
    protected void setCacheValue(T newValue) {
        this.cache = newValue;
        if(this.field!=null) this.field.setText(String.valueOf(newValue));
    }

    public static class GuiFilterTextField extends TextFieldWidget {
        private Predicate<Character> typedCharFilter;
        private final String defaultText;
        public GuiFilterTextField(String defaultText, FontRenderer fontrenderer, int x, int y, int par5Width, int par6Height) {
            super(fontrenderer, x, y, par5Width, par6Height, ITextComponent.getTextComponentOrEmpty(null));
            this.setText(defaultText);
            this.defaultText = defaultText;
        }

        public GuiFilterTextField setTypedCharFilter(Predicate<Character> filter) {
            this.typedCharFilter = filter;
            return this;
        }

        @Override
        public boolean charTyped(char typedChar, int keyCode) {
            boolean typedCharTest = this.typedCharFilter != null && this.typedCharFilter.test(typedChar);
            if(typedCharTest && this.isFocused()) {
                if(this.defaultText.equals(this.getText())) {
                    this.setText("");
                }
                boolean flag = super.charTyped(typedChar, keyCode);

                if(this.getText().isEmpty()) {
                    this.setText(this.defaultText);
                }
                return flag;
            }
            return false;
        }
    }
}
