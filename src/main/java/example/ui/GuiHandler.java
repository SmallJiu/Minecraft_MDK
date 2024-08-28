package example.ui;

import cat.jiu.caption.ExampleConfig;
import cat.jiu.core.util.client.config.GuiConfig;
import example.ExampleModMain;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class GuiHandler {
	public static final DeferredRegister<MenuType<?>> MENU_TYPE_REGISTER = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ExampleModMain.MODID);
	private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerType(String id, IContainerFactory<T> factory) {
		return MENU_TYPE_REGISTER.register(id, ()->new MenuType<>(factory, FeatureFlagSet.of()));
	}


	@OnlyIn(Dist.CLIENT)
	public static void registerScreen() {
		ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, ()->new ConfigScreenHandler.ConfigScreenFactory((mc, parent)->
				new GuiConfig("/config/examplemod.toml", parent, ExampleConfig.CONFIG_MAIN)
		));
	}


	private static final Map<Integer, GuiConsumer> GUIS = new HashMap<>();

	public static boolean register(int id, GuiConsumer supplier){
		if (!GUIS.containsKey(id)) {
			GUIS.put(id, supplier);
			return true;
		}
		return false;
	}

	public static void openGui(int ID, ServerPlayer player) {
		NetworkHooks.openScreen(player,
				new SimpleMenuProvider(
						(guiID, inventory, p) -> GUIS.containsKey(ID) && GUIS.get(ID) != null ? GUIS.get(ID).container.createMenu(guiID, inventory, player) : null,
						Component.empty()
				),
				buffer -> {
					if (GUIS.containsKey(ID) && GUIS.get(ID) != null){
						GUIS.get(ID).extraDataWriter.accept(player, buffer);
					}
				});
	}

	public static record GuiConsumer(MenuConstructor container, BiConsumer<ServerPlayer, FriendlyByteBuf> extraDataWriter) { }
}
