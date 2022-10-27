package net.p3pp3rf1y.sophisticatedbackpacksvh.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.p3pp3rf1y.sophisticatedbackpacks.api.CapabilityBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class SnapshotStorage extends SavedData {
	private final Map<UUID, CompoundTag> backpackSnapshots = new HashMap<>();
	private static final SnapshotStorage emptySnapshotStorage = new SnapshotStorage();

	public static SnapshotStorage get() {
		if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
			MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
			if (server != null) {
				ServerLevel overworld = server.getLevel(Level.OVERWORLD);
				DimensionDataStorage storage = overworld.getDataStorage();
				return storage.computeIfAbsent(SnapshotStorage::load, SnapshotStorage::new, "sbsnapshots");
			}
		}

		return emptySnapshotStorage;
	}

	public void createSnapshot(ItemStack backpack) {
		backpack.getCapability(CapabilityBackpackWrapper.getCapabilityInstance()).ifPresent(backpackWrapper -> backpackWrapper.getContentsUuid().ifPresent(backpackUuid -> {
			backpackSnapshots.put(backpackUuid, BackpackStorage.get().getOrCreateBackpackContents(backpackUuid).copy());
			setDirty();
		}));
	}

	public void restoreContentsFromSnapshot(ItemStack backpack) {
		backpack.getCapability(CapabilityBackpackWrapper.getCapabilityInstance()).ifPresent(backpackWrapper -> {
			backpackWrapper.getContentsUuid().ifPresent(backpackUuid -> {
				if (backpackSnapshots.containsKey(backpackUuid)) {
					BackpackStorage.get().setBackpackContents(backpackUuid, backpackSnapshots.get(backpackUuid).copy());
				}
			});
			backpackWrapper.onContentsNbtUpdated();
		});
	}

	public static SnapshotStorage load(CompoundTag nbt) {
		SnapshotStorage storage = new SnapshotStorage();
		readSnapshotContents(nbt, storage);
		return storage;
	}

	private static void readSnapshotContents(CompoundTag nbt, SnapshotStorage storage) {
		for (Tag n : nbt.getList("backpackSnapshots", 10)) {
			CompoundTag uuidContentsPair = (CompoundTag) n;
			UUID uuid = NbtUtils.loadUUID(Objects.requireNonNull(uuidContentsPair.get("uuid")));
			CompoundTag contents = uuidContentsPair.getCompound("contents");
			storage.backpackSnapshots.put(uuid, contents);
		}
	}

	@Override
	public CompoundTag save(CompoundTag compoundTag) {
		CompoundTag ret = new CompoundTag();
		writeSnapshotContents(ret);
		return ret;
	}

	private void writeSnapshotContents(CompoundTag ret) {
		ListTag backpackContentsNbt = new ListTag();

		for (Map.Entry<UUID, CompoundTag> entry : this.backpackSnapshots.entrySet()) {
			CompoundTag uuidContentsPair = new CompoundTag();
			uuidContentsPair.put("uuid", NbtUtils.createUUID(entry.getKey()));
			uuidContentsPair.put("contents", entry.getValue());
			backpackContentsNbt.add(uuidContentsPair);
		}

		ret.put("backpackSnapshots", backpackContentsNbt);
	}
}
