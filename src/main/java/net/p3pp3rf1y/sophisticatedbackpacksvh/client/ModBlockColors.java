package net.p3pp3rf1y.sophisticatedbackpacksvh.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackBlockEntity;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedcore.util.WorldHelper;

import static net.p3pp3rf1y.sophisticatedbackpacks.init.ModBlocks.*;

public class ModBlockColors {
	private ModBlockColors() {}

	public static void init() {
		BlockColors blockColors = Minecraft.getInstance().getBlockColors();
		blockColors.register((state, blockDisplayReader, pos, tintIndex) -> {
			if (tintIndex < 0 || tintIndex > 1 || pos == null) {
				return -1;
			}
			return WorldHelper.getBlockEntity(blockDisplayReader, pos, BackpackBlockEntity.class)
					.map(te -> {
						if (tintIndex == 0) {
							int mainColor = te.getBackpackWrapper().getMainColor();
							return mainColor == BackpackWrapper.DEFAULT_CLOTH_COLOR ? DefaultBackpackColors.getDefaultMainColor(te.getBackpackWrapper().getBackpack().getItem()) : mainColor;
						}
						int accentColor = te.getBackpackWrapper().getAccentColor();
						return accentColor == BackpackWrapper.DEFAULT_BORDER_COLOR ? DefaultBackpackColors.getDefaultAccentColor(te.getBackpackWrapper().getBackpack().getItem()) : accentColor;
					})
					.orElse(getDefaultColor(tintIndex));
		}, BACKPACK.get(), IRON_BACKPACK.get(), GOLD_BACKPACK.get(), DIAMOND_BACKPACK.get(), NETHERITE_BACKPACK.get());
	}

	private static int getDefaultColor(int tintIndex) {
		return tintIndex == 0 ? BackpackWrapper.DEFAULT_CLOTH_COLOR : BackpackWrapper.DEFAULT_BORDER_COLOR;
	}
}
