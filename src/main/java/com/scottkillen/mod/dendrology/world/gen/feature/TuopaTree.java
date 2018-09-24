package com.scottkillen.mod.dendrology.world.gen.feature;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TuopaTree extends AbstractTree
{
    public TuopaTree(boolean fromSapling) { super(fromSapling); }

    public TuopaTree() { this(true); }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
    	int x = pos.getX();
    	int y = pos.getY();
    	int z = pos.getZ();
        final Random rng = new Random();
        rng.setSeed(rand.nextLong());

        final int height =
                1 + (rng.nextInt(7) < 2 ? 1 : 0) + (rng.nextInt(7) < 2 ? 1 : 0) + (rng.nextBoolean() ? 1 : 0);

        if (isPoorGrowthConditions(world, x, y, z, height, getSaplingBlock())) return false;

        final IBlockState state = world.getBlockState(pos.down());
        state.getBlock().onPlantGrow(state, world, pos.down(), pos);     

        for (int level = 0; level <= 6 * height + 1; level++)
        {
            if (level != 6 * height + 1) placeLog(world, new BlockPos(x, y + level, z));

            if (height == 1 && level > 2) for (int dX = -1; dX <= 1; dX++)
            {
                for (int dZ = -1; dZ <= 1; dZ++)
                    if (Math.abs(dX) != 1 || Math.abs(dZ) != 1) placeLeaves(world, new BlockPos(x + dX, y + level, z + dZ));
            }

            if (height == 2 && level > 2) for (int dX = -2; dX <= 2; dX++)
                for (int dZ = -2; dZ <= 2; dZ++)
                {
                    if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                        placeLeaves(world, new BlockPos(x + dX, y + level, z + dZ));

                    if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && level == 7) placeLeaves(world, new BlockPos(x + dX, y + 7, z + dZ));

                    if ((Math.abs(dX) != 2 || Math.abs(dZ) != 2) && (Math.abs(dX) != 2 || Math.abs(dZ) != 1) &&
                            (Math.abs(dX) != 1 || Math.abs(dZ) != 2) && level <= 6 * height - 1 && level > 3)
                        placeLeaves(world, new BlockPos(x + dX, y + level, z + dZ));
                }

            if (height == 3 && level > 2) for (int dX = -2; dX <= 2; dX++)
                for (int dZ = -2; dZ <= 2; dZ++)
                {
                    if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                        placeLeaves(world, new BlockPos(x + dX, y + level, z + dZ));

                    if ((Math.abs(dX) != 2 || Math.abs(dZ) != 2) && (Math.abs(dX) != 2 || Math.abs(dZ) != 1) &&
                            (Math.abs(dX) != 1 || Math.abs(dZ) != 2) && level <= 6 * height && level > 3)
                        placeLeaves(world, new BlockPos(x + dX, y + level, z + dZ));
                }

            if (height == 4 && level > 2) for (int dX = -3; dX <= 3; dX++)
                for (int dZ = -3; dZ <= 3; dZ++)
                {
                    if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                        placeLeaves(world, new BlockPos(x + dX, y + level, z + dZ));

                    if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && level <= 14 && level >= 2)
                        placeLeaves(world, new BlockPos(x + dX, y + level, z + dZ));

                    if (Math.abs(dX) <= 2 && Math.abs(dZ) <= 2 && (Math.abs(dX) != 2 || Math.abs(dZ) != 2) &&
                            (level == 6 * height || level == 5)) placeLeaves(world, new BlockPos(x + dX, y + level, z + dZ));

                    if ((Math.abs(dX) != 3 || Math.abs(dZ) != 3) && (Math.abs(dX) != 3 || Math.abs(dZ) != 2) &&
                            (Math.abs(dX) != 2 || Math.abs(dZ) != 3) && level <= 6 * height - 1 && level > 5)
                        placeLeaves(world, new BlockPos(x + dX, y + level, z + dZ));
                }
        }

        return true;
    }
}
