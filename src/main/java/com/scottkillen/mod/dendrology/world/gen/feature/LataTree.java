package com.scottkillen.mod.dendrology.world.gen.feature;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LataTree extends AbstractTree
{
    private int logDirection = 0;

    public LataTree(boolean fromSapling) { super(fromSapling); }

    public LataTree() { this(true); }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
    	int x = pos.getX();
    	int y = pos.getY();
    	int z = pos.getZ();
        final Random rng = new Random();
        rng.setSeed(rand.nextLong());

        final int height = rng.nextInt(15) + 6;

        if (isPoorGrowthConditions(world, x, y, z, height, getSaplingBlock())) return false;

        final IBlockState state = world.getBlockState(pos.down());
        state.getBlock().onPlantGrow(state, world, pos.down(), pos); 

        for (int level = 0; level <= height; level++)
        {
            if (level == height) leafGen(world, x, y + level, z);
            else placeLog(world, new BlockPos(x, y + level, z));

            if (level > 3 && level < height)
            {
                final int branchRarity = height / level + 1;

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, x, y, z, height, level, -1, 0);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, x, y, z, height, level, 1, 0);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, x, y, z, height, level, 0, -1);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, x, y, z, height, level, 0, 1);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, x, y, z, height, level, -1, 1);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, x, y, z, height, level, -1, -1);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, x, y, z, height, level, 1, 1);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, x, y, z, height, level, 1, -1);
            }
        }

        return true;
    }

    private void branch(World world, Random rand, int x, int y, int z, int treeHeight, int branchLevel, int dX, int dZ)
    {
        final int length = treeHeight - branchLevel;

        int x1 = x;
        int y1 = y + branchLevel;
        int z1 = z;

        for (int i = 0; i <= length; i++)
        {
            if (dX == -1 && rand.nextInt(3) > 0)
            {
                x1--;
                logDirection = 4;

                if (dZ == 0 && rand.nextInt(4) == 0) z1 += rand.nextInt(3) - 1;
            } else if (dX == 1 && rand.nextInt(3) > 0)
            {
                x1++;
                logDirection = 4;

                if (dZ == 0 && rand.nextInt(4) == 0) z1 += rand.nextInt(3) - 1;
            }

            if (dZ == -1 && rand.nextInt(3) > 0)
            {
                z1--;
                logDirection = 8;

                if (dX == 0 && rand.nextInt(4) == 0) x1 += rand.nextInt(3) - 1;
            } else if (dZ == 1 && rand.nextInt(3) > 0)
            {
                z1++;
                logDirection = 8;

                if (dX == 0 && rand.nextInt(4) == 0) x1 += rand.nextInt(3) - 1;
            }

            placeLog(world, new BlockPos(x1, y1, z1));
            logDirection = 0;

            if (rand.nextInt(3) == 0)
            {
                leafGen(world, x1, y1, z1);
            }

            if (rand.nextInt(3) > 0)
            {
                y1++;
            }

            if (i == length)
            {
                placeLog(world, new BlockPos(x1, y1, z1));
                leafGen(world, x1, y1, z1);
            }
        }
    }

    private void leafGen(World world, int x, int y, int z)
    {
        for (int dX = -3; dX <= 3; dX++)
            for (int dZ = -3; dZ <= 3; dZ++)
            {
                if ((Math.abs(dX) != 3 || Math.abs(dZ) != 3) && (Math.abs(dX) != 2 || Math.abs(dZ) != 3) &&
                        (Math.abs(dX) != 3 || Math.abs(dZ) != 2)) placeLeaves(world, new BlockPos(x + dX, y, z + dZ));

                if (Math.abs(dX) < 3 && Math.abs(dZ) < 3 && (Math.abs(dX) != 2 || Math.abs(dZ) != 2))
                {
                    placeLeaves(world, new BlockPos(x + dX, y + 1, z + dZ));
                    placeLeaves(world, new BlockPos(x + dX, y - 1, z + dZ));
                }

                if (Math.abs(dX) + Math.abs(dZ) < 2)
                {
                    placeLeaves(world, new BlockPos(x + dX, y + 2, z + dZ));
                    placeLeaves(world, new BlockPos(x + dX, y - 2, z + dZ));
                }
            }
    }

    @Override
    protected int getLogMetadata() {return super.getLogMetadata() | logDirection;}
}
