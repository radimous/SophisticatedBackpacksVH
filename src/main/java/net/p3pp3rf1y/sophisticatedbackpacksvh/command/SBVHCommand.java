package net.p3pp3rf1y.sophisticatedbackpacksvh.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacksvh.data.SnapshotStorage;

public class SBVHCommand {
	private static final int OP_LEVEL = 2;

	private SBVHCommand() {}

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("sbvh").requires(cs -> cs.hasPermission(OP_LEVEL))
				.then(CreateSnapshotCommand.register())
				.then(RestoreFromSnapshotCommand.register())
		);
	}

	private static class CreateSnapshotCommand {
		static ArgumentBuilder<CommandSourceStack, ?> register() {
			return Commands.literal("createSnapshot").executes((context) -> {
				ServerPlayer player = context.getSource().getPlayerOrException();
				ItemStack mainHandItem = player.getMainHandItem();
				if (mainHandItem.getItem() instanceof BackpackItem) {
					SnapshotStorage.get().makeSnapshot(mainHandItem);
				}
				return 0;
			});
		}
	}

	private static class RestoreFromSnapshotCommand {
		static ArgumentBuilder<CommandSourceStack, ?> register() {
			return Commands.literal("restoreFromSnapshot").executes((context) -> {
				ServerPlayer player = context.getSource().getPlayerOrException();
				ItemStack mainHandItem = player.getMainHandItem();
				if (mainHandItem.getItem() instanceof BackpackItem) {
					SnapshotStorage.get().restoreContentsFromSnapshot(mainHandItem);
				}
				return 0;
			});
		}
	}
}