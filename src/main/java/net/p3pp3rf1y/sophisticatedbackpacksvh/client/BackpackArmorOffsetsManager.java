package net.p3pp3rf1y.sophisticatedbackpacksvh.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.math.Vector3d;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.p3pp3rf1y.sophisticatedbackpacks.registry.IRegistryDataLoader;
import net.p3pp3rf1y.sophisticatedcore.util.RegistryHelper;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BackpackArmorOffsetsManager {
	private static final Map<Item, Vector3d> BACKPACK_DEFAULT_TRANSLATIONS = new HashMap<>();
	private static final Map<Item, Map<Item, Vector3d>> BACKPACK_ARMOR_TRANSLATIONS = new HashMap<>();

	public static Optional<Vector3d> getOffsets(Item backpackItem, Item armorItem) {
		if (BACKPACK_ARMOR_TRANSLATIONS.containsKey(backpackItem)) {
			Map<Item, Vector3d> armorTranslations = BACKPACK_ARMOR_TRANSLATIONS.get(backpackItem);
			if (armorTranslations.containsKey(armorItem)) {
				return Optional.of(armorTranslations.get(armorItem));
			}
		}
		if (BACKPACK_DEFAULT_TRANSLATIONS.containsKey(backpackItem)) {
			return Optional.of(BACKPACK_DEFAULT_TRANSLATIONS.get(backpackItem));
		}
		return Optional.empty();
	}

	public static class Loader implements IRegistryDataLoader {
		@Override
		public String getName() {
			return "armor_offsets";
		}

		@Override
		public void parse(JsonObject json, @Nullable String modId) {
			for (Map.Entry<String, JsonElement> property : json.entrySet()) {
				RegistryHelper.getItemFromName(property.getKey()).ifPresent(backpackItem -> parseBackpackOffsets(property, backpackItem));
			}
		}

		private void parseBackpackOffsets(Map.Entry<String, JsonElement> property, Item backpackItem) {
			if (property.getValue().isJsonObject()) {
				JsonObject settings = property.getValue().getAsJsonObject();
				if (settings.has("default")) {
					JsonElement defOffs = settings.get("default");
					if (defOffs.isJsonObject()) {
						BACKPACK_DEFAULT_TRANSLATIONS.put(backpackItem, getOffsets(defOffs.getAsJsonObject()));
					}
				}
				if (settings.has("overrides")) {
					JsonElement ovs = settings.get("overrides");
					if (ovs.isJsonArray()) {
						for (JsonElement override : ovs.getAsJsonArray()) {
							if (override.isJsonObject()) {
								parseOverride(backpackItem, override.getAsJsonObject());
							}
						}
					}
				}
			}
		}

		private void parseOverride(Item backpackItem, JsonObject overrideJson) {
			if (overrideJson.has("offsets")) {
				JsonElement offsetsJson = overrideJson.get("offsets");
				if (offsetsJson.isJsonObject()) {
					Vector3d offsets = getOffsets(offsetsJson.getAsJsonObject());
					if (overrideJson.has("armor")) {

						JsonElement armorJson = overrideJson.get("armor");
						if (armorJson.isJsonPrimitive()) {
							RegistryHelper.getItemFromName(armorJson.getAsString()).ifPresent(armorItem -> BACKPACK_ARMOR_TRANSLATIONS.computeIfAbsent(backpackItem, bi -> new HashMap<>()).put(armorItem, offsets));
						} else if (armorJson.isJsonArray()) {
							for (var armor : armorJson.getAsJsonArray()) {
								if (armor.isJsonPrimitive()) {
									RegistryHelper.getItemFromName(armor.getAsString()).ifPresent(armorItem -> BACKPACK_ARMOR_TRANSLATIONS.computeIfAbsent(backpackItem, bi -> new HashMap<>()).put(armorItem, offsets));
								}
							}
						}
					}
				}
			}
		}

		private Vector3d getOffsets(JsonObject offsetsJson) {
			return new Vector3d(GsonHelper.getAsDouble(offsetsJson, "x", 0), GsonHelper.getAsDouble(offsetsJson, "y", 0), GsonHelper.getAsDouble(offsetsJson, "z", 0));
		}

		@Override
		public void clear() {
			BACKPACK_DEFAULT_TRANSLATIONS.clear();
			BACKPACK_ARMOR_TRANSLATIONS.clear();
		}
	}
}
