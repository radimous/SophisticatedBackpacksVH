package net.p3pp3rf1y.sophisticatedbackpacksvh;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackShapes;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModBlocks;

import java.util.HashMap;
import java.util.Map;

public class BackpackShapeProvider implements BackpackShapes.IShapeProvider {

	private static final Map<Block, BackpackShapes.RotatedShapes> BACKPACK_SHAPES = ImmutableMap.of(
			ModBlocks.BACKPACK.get(), new BackpackShapes.RotatedShapes(
					Block.box(4, 2, 6, 12, 4, 6),
					Block.box(9.5, 0, 3.9, 12.5, 4, 5.9),
					Block.box(3.5, 0, 3.9, 6.5, 4, 5.9),
					Block.box(9.5, 0, 10.1, 12.5, 4, 12.1),
					Block.box(3.5, 0, 10.1, 6.5, 4, 12.1),
					Block.box(12, 2, 6, 12, 4, 10),
					Block.box(4, 2, 10, 12, 4, 10),
					Block.box(4, 2, 6, 4, 4, 10)
			),
			ModBlocks.IRON_BACKPACK.get(), new BackpackShapes.RotatedShapes(
					Block.box(6, 0, 6, 10, 5, 9),
					Block.box(7.5, 2.8, 8.4, 8.5, 3.8, 9.4)
			),
			ModBlocks.GOLD_BACKPACK.get(), new BackpackShapes.RotatedShapes(
					Block.box(3, 0, 6.5, 7, 6, 9.5),
					Block.box(4.5, 3.8, 8.9, 5.5, 4.8, 9.9),
					Block.box(10.5, 3.8, 8.9, 11.5, 4.8, 9.9),
					Block.box(9, 0, 6.5, 13, 6, 9.5)
			),
			ModBlocks.DIAMOND_BACKPACK.get(), new BackpackShapes.RotatedShapes(
					Block.box(4, 0, 5, 12, 6, 10),
					Block.box(4, 6, 5, 12, 7, 11),
					Block.box(7, 2, 10, 9, 6, 11)
			),
			ModBlocks.NETHERITE_BACKPACK.get(), new BackpackShapes.RotatedShapes(
					Block.box(4, 6, 4, 12, 12, 9),
					Block.box(4, 12, 4, 12, 13, 10),
					Block.box(7, 8, 9, 9, 12, 10),
					Block.box(13, 0, 5, 14, 4, 8),
					Block.box(2, 0, 5, 3, 4, 8),
					Block.box(3, 8, 5, 4, 12, 8),
					Block.box(3, 0, 4, 13, 7, 11),
					Block.box(12, 8, 5, 13, 12, 8)
			)
	);

	private static final Map<Integer, VoxelShape> SHAPES = new HashMap<>();

	@Override
	public VoxelShape getShape(Block backpackBlock, Direction dir, boolean leftTank, boolean rightTank, boolean battery) {
		if (BACKPACK_SHAPES.containsKey(backpackBlock)) {
			int key = getKey(backpackBlock, dir);
			return SHAPES.computeIfAbsent(key, k -> composeShape(backpackBlock, dir));
		}

		return BackpackShapes.DEFAULT_SHAPE_PROVIDER.getShape(backpackBlock, dir, leftTank, rightTank, battery);
	}

	private int getKey(Block backpackBlock, Direction dir) {
		return backpackBlock.delegate.hashCode() << 2 + dir.get2DDataValue();
	}

	private VoxelShape composeShape(Block backpackBlock, Direction dir) {
		return BACKPACK_SHAPES.get(backpackBlock).getRotatedShape(dir);
	}
}
