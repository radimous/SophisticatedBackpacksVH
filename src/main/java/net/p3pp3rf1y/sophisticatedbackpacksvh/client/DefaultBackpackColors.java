package net.p3pp3rf1y.sophisticatedbackpacksvh.client;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.item.Item;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;

import java.util.Map;

public class DefaultBackpackColors {
	private DefaultBackpackColors() {}

	private static final Map<Item, Integer> MAIN_COLORS = ImmutableMap.of(
			ModItems.BACKPACK.get(), 0x7d584a,
			ModItems.IRON_BACKPACK.get(), 0xb41f1f,
			ModItems.GOLD_BACKPACK.get(), 0xff3c3c,
			ModItems.DIAMOND_BACKPACK.get(), 0xaa8679,
			ModItems.NETHERITE_BACKPACK.get(), 0x956655
	);
	private static final Map<Item, Integer> ACCENT_COLORS = ImmutableMap.of(
			ModItems.BACKPACK.get(), 0x332018,
			ModItems.IRON_BACKPACK.get(), 0xb41f1f,
			ModItems.GOLD_BACKPACK.get(), 0xa41717,
			ModItems.DIAMOND_BACKPACK.get(), 0x573428,
			ModItems.NETHERITE_BACKPACK.get(), 0x5b372b
	);

	public static int getDefaultMainColor(Item backpackItem) {
		return MAIN_COLORS.getOrDefault(backpackItem, BackpackWrapper.DEFAULT_CLOTH_COLOR);
	}

	public static int getDefaultAccentColor(Item backpackItem) {
		return ACCENT_COLORS.getOrDefault(backpackItem, BackpackWrapper.DEFAULT_BORDER_COLOR);
	}
}
