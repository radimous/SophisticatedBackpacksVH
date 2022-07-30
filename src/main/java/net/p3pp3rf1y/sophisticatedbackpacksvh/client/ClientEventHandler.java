package net.p3pp3rf1y.sophisticatedbackpacksvh.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.p3pp3rf1y.sophisticatedbackpacks.client.render.BackpackModel;
import net.p3pp3rf1y.sophisticatedbackpacks.client.render.BackpackModelManager;
import net.p3pp3rf1y.sophisticatedbackpacks.client.render.IBackpackModel;
import net.p3pp3rf1y.sophisticatedbackpacks.client.render.IBackpackModelProvider;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedbackpacksvh.SophisticatedBackpacksVH;

import java.util.HashMap;
import java.util.Map;

import static net.p3pp3rf1y.sophisticatedbackpacks.client.ClientEventHandler.BACKPACK_LAYER;

public class ClientEventHandler {
	public static final ModelLayerLocation BASIC_BACKPACK_LAYER = new ModelLayerLocation(new ResourceLocation(SophisticatedBackpacksVH.MOD_ID, "basic_backpack"), "main");
	public static final ModelLayerLocation IRON_BACKPACK_LAYER = new ModelLayerLocation(new ResourceLocation(SophisticatedBackpacksVH.MOD_ID, "iron_backpack"), "main");
	public static final ModelLayerLocation GOLD_BACKPACK_LAYER = new ModelLayerLocation(new ResourceLocation(SophisticatedBackpacksVH.MOD_ID, "gold_backpack"), "main");
	public static final ModelLayerLocation DIAMOND_BACKPACK_LAYER = new ModelLayerLocation(new ResourceLocation(SophisticatedBackpacksVH.MOD_ID, "diamond_backpack"), "main");
	public static final ModelLayerLocation NETHERITE_BACKPACK_LAYER = new ModelLayerLocation(new ResourceLocation(SophisticatedBackpacksVH.MOD_ID, "netherite_backpack"), "main");

	public static void registerHandlers() {
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		modBus.addListener(ClientEventHandler::registerLayer);
		modBus.addListener(ClientEventHandler::loadComplete);
		BackpackModelManager.registerBackpackModelProvider(new IBackpackModelProvider() {
			private final Map<Item, IBackpackModel> backpackModels = new HashMap<>();
			private IBackpackModel defaultModel;
			@Override
			public void initModels() {
				if (defaultModel == null) {
					EntityModelSet entityModels = Minecraft.getInstance().getEntityModels();
					defaultModel = new BackpackModel(entityModels.bakeLayer(BACKPACK_LAYER));
					backpackModels.put(ModItems.BACKPACK.get(), new BasicBackpackModel<>(entityModels.bakeLayer(BASIC_BACKPACK_LAYER)));
					backpackModels.put(ModItems.IRON_BACKPACK.get(), new IronBackpackModel<>(entityModels.bakeLayer(IRON_BACKPACK_LAYER)));
					backpackModels.put(ModItems.GOLD_BACKPACK.get(), new GoldBackpackModel<>(entityModels.bakeLayer(GOLD_BACKPACK_LAYER)));
					backpackModels.put(ModItems.DIAMOND_BACKPACK.get(), new DiamondBackpackModel<>(entityModels.bakeLayer(DIAMOND_BACKPACK_LAYER)));
					backpackModels.put(ModItems.NETHERITE_BACKPACK.get(), new NetheriteBackpackModel<>(entityModels.bakeLayer(NETHERITE_BACKPACK_LAYER)));
				}
			}

			@Override
			public void initModels(BlockEntityRendererProvider.Context context) {
				if (defaultModel == null) {
					defaultModel = new BackpackModel(context.bakeLayer(BACKPACK_LAYER));
					backpackModels.put(ModItems.BACKPACK.get(), new BasicBackpackModel<>(context.bakeLayer(BASIC_BACKPACK_LAYER)));
					backpackModels.put(ModItems.IRON_BACKPACK.get(), new IronBackpackModel<>(context.bakeLayer(IRON_BACKPACK_LAYER)));
					backpackModels.put(ModItems.GOLD_BACKPACK.get(), new GoldBackpackModel<>(context.bakeLayer(GOLD_BACKPACK_LAYER)));
					backpackModels.put(ModItems.DIAMOND_BACKPACK.get(), new DiamondBackpackModel<>(context.bakeLayer(DIAMOND_BACKPACK_LAYER)));
					backpackModels.put(ModItems.NETHERITE_BACKPACK.get(), new NetheriteBackpackModel<>(context.bakeLayer(NETHERITE_BACKPACK_LAYER)));
				}
			}

			@Override
			public IBackpackModel getBackpackModel(Item backpackItem) {
				return backpackModels.getOrDefault(backpackItem, defaultModel);
			}
		});
	}

	private static void loadComplete(FMLLoadCompleteEvent event) {
		event.enqueueWork(() -> {
			ModItemColors.init();
			ModBlockColors.init();
		});
	}

	public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(BASIC_BACKPACK_LAYER, BasicBackpackModel::createBodyLayer);
		event.registerLayerDefinition(IRON_BACKPACK_LAYER, IronBackpackModel::createBodyLayer);
		event.registerLayerDefinition(GOLD_BACKPACK_LAYER, GoldBackpackModel::createBodyLayer);
		event.registerLayerDefinition(DIAMOND_BACKPACK_LAYER, DiamondBackpackModel::createBodyLayer);
		event.registerLayerDefinition(NETHERITE_BACKPACK_LAYER, NetheriteBackpackModel::createBodyLayer);
	}
}
