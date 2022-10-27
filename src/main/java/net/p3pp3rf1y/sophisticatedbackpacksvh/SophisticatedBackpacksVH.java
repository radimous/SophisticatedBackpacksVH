package net.p3pp3rf1y.sophisticatedbackpacksvh;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackShapes;
import net.p3pp3rf1y.sophisticatedbackpacks.registry.RegistryLoader;
import net.p3pp3rf1y.sophisticatedbackpacksvh.client.BackpackArmorOffsetsManager;
import net.p3pp3rf1y.sophisticatedbackpacksvh.client.ClientEventHandler;
import net.p3pp3rf1y.sophisticatedbackpacksvh.command.SBVHCommand;

@Mod(SophisticatedBackpacksVH.MOD_ID)
public class SophisticatedBackpacksVH {
	public static final String MOD_ID = "sophisticatedbackpacksvh";

	public SophisticatedBackpacksVH() {
		if (FMLEnvironment.dist == Dist.CLIENT) {
			ClientEventHandler.registerHandlers();
		}
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		modBus.addListener(this::onLoadComplete);
		MinecraftForge.EVENT_BUS.addListener(this::registerCommands);

		RegistryLoader.registerParser(new BackpackArmorOffsetsManager.Loader());
	}

	private void onLoadComplete(FMLLoadCompleteEvent event) {
		BackpackShapes.setShapeProvider(new BackpackShapeProvider());
	}

	private void registerCommands(RegisterCommandsEvent event) {
		SBVHCommand.register(event.getDispatcher());
	}
}
