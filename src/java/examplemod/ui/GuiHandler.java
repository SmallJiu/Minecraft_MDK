package examplemod.ui;


import examplemod.ExampleMod;
import examplemod.configs.ExampleConfig;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class GuiHandler {
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPE_REGISTER = DeferredRegister.create(ForgeRegistries.CONTAINERS, ExampleMod.MODID);

	private static <T extends Container> RegistryObject<ContainerType<T>> registerType(String id, IContainerFactory<T> factory) {
		return CONTAINER_TYPE_REGISTER.register(id, ()->IForgeContainerType.create(factory));
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerScreen() {
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, ()->(mc, parent)->
				new examplemod.configs.gui.GuiConfig("/config/examplemod.toml", parent, ExampleConfig.CONFIG_MAIN)
		);
	}

	private static final Map<Integer, GuiConsumer> GUIS = new HashMap<>();
	public static boolean register(int id, GuiConsumer supplier){
		if (!GUIS.containsKey(id)) {
			GUIS.put(id, supplier);
			return true;
		}
		return false;
	}

	public static void openGui(int ID, ServerPlayerEntity player) {
		NetworkHooks.openGui(player,
				new SimpleNamedContainerProvider(
						(guiID, inventory, p) -> GUIS.containsKey(ID) && GUIS.get(ID) != null ? GUIS.get(ID).container.createMenu(guiID, inventory, player) : null,
						ITextComponent.getTextComponentOrEmpty(null)
				),
				buffer -> {
					if (GUIS.containsKey(ID) && GUIS.get(ID) != null){
						GUIS.get(ID).extraDataWriter.accept(player, buffer);
					}
				});
	}

	public static class GuiConsumer {
		public final IContainerProvider container;
		public final BiConsumer<ServerPlayerEntity, PacketBuffer> extraDataWriter;

		public GuiConsumer(IContainerProvider container, BiConsumer<ServerPlayerEntity, PacketBuffer> extraDataWriter) {
			this.container = container;
			this.extraDataWriter = extraDataWriter;
		}
	}
}
