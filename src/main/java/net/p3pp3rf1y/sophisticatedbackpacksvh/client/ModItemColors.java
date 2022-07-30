package net.p3pp3rf1y.sophisticatedbackpacksvh.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.p3pp3rf1y.sophisticatedbackpacks.api.CapabilityBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;

import static net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems.*;

public class ModItemColors {
	private ModItemColors() {}

	public static void init() {
		ItemColors itemColors = Minecraft.getInstance().getItemColors();

		itemColors.register((backpack, layer) -> {
			if (layer > 1 || !(backpack.getItem() instanceof BackpackItem)) {
				return -1;
			}
			return backpack.getCapability(CapabilityBackpackWrapper.getCapabilityInstance()).map(backpackWrapper -> {
				if (layer == 0) {
					int mainColor = backpackWrapper.getMainColor();
					return mainColor == BackpackWrapper.DEFAULT_CLOTH_COLOR ? DefaultBackpackColors.getDefaultMainColor(backpack.getItem()) : mainColor;
				} else if (layer == 1) {
					int accentColor = backpackWrapper.getAccentColor();
					return accentColor == BackpackWrapper.DEFAULT_BORDER_COLOR ? DefaultBackpackColors.getDefaultAccentColor(backpack.getItem()) : accentColor;
				}
				return -1;
			}).orElse(-1);
		}, BACKPACK.get(), IRON_BACKPACK.get(), GOLD_BACKPACK.get(), DIAMOND_BACKPACK.get(), NETHERITE_BACKPACK.get());
	}
}
