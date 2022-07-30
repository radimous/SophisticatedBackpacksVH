package net.p3pp3rf1y.sophisticatedbackpacksvh;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackShapes;
import net.p3pp3rf1y.sophisticatedbackpacks.registry.RegistryLoader;
import net.p3pp3rf1y.sophisticatedbackpacksvh.client.BackpackArmorOffsetsManager;
import net.p3pp3rf1y.sophisticatedbackpacksvh.client.ClientEventHandler;

@Mod(SophisticatedBackpacksVH.MOD_ID)
public class SophisticatedBackpacksVH {
	public static final String MOD_ID = "sophisticatedbackpacksvh";

	public SophisticatedBackpacksVH() {
		if (FMLEnvironment.dist == Dist.CLIENT) {
			ClientEventHandler.registerHandlers();
		}
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		modBus.addListener(this::onLoadComplete);

		RegistryLoader.registerParser(new BackpackArmorOffsetsManager.Loader());
	}

	private void onLoadComplete(FMLLoadCompleteEvent event) {
		BackpackShapes.setShapeProvider(new BackpackShapeProvider());
	}
}
