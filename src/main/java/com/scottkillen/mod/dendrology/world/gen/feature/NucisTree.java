package com.scottkillen.mod.dendrology.world.gen.feature;

import java.util.Random;

import com.google.common.base.Objects;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NucisTree extends AbstractTree
{
    private int logDirection = 0;

    public NucisTree(boolean fromSapling) { super(fromSapling); }

    public NucisTree() { this(true); }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
    	int x = pos.getX();
    	int y = pos.getY();
    	int z = pos.getZ();
        final Random rng = new Random();
        rng.setSeed(rand.nextLong());

        final int height = rng.nextInt(15) + 8;

        if (isPoorGrowthConditions(world, x, y, z, height, getSaplingBlock())) return false;

        final IBlockState state = world.getBlockState(pos.down());
        state.getBlock().onPlantGrow(state, world, pos.down(), pos);   

        for (int level = 0; level < height; level++)
        {
            placeLog(world, new BlockPos(x, y + level, z));

            if (level > 3)
            {
                final int branchRarity = height / (level - 2) + 1;

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

        leafGen(world, x, y + height, z);

        return true;
    }

    private void branch(World world, Random random, int x, int y, int z, int height, int level, int dX, int dZ)
    {
        int level1 = level + y;
        final int lengthToGo = height - level1;

        int x1 = x;
        int z1 = z;

        int index = 0;
        while (index <= lengthToGo)
        {
            if (dX == -1 && random.nextInt(3) > 0)
            {
                x1--;
                logDirection = 4;

                if (dZ == 0 && random.nextInt(4) == 0) z1 = z1 + random.nextInt(3) - 1;
            }
            else if (dX == 1 && random.nextInt(3) > 0)
            {
                x1++;
                logDirection = 4;

                if (dZ == 0 && random.nextInt(4) == 0) z1 = z1 + random.nextInt(3) - 1;
            }

            if (dZ == -1 && random.nextInt(3) > 0)
            {
                z1--;
                logDirection = 8;

                if (dX == 0 && random.nextInt(4) == 0) x1 = x1 + random.nextInt(3) - 1;
            } else if (dZ == 1 && random.nextInt(3) > 0)
            {
                z1++;
                logDirection = 8;

                if (dX == 0 && random.nextInt(4) == 0) x1 = x1 + random.nextInt(3) - 1;
            }

            placeLog(world, new BlockPos(x1, level1, z1));

            if (random.nextInt(3) > 0) level1++;

            if (index == lengthToGo || random.nextInt(6) == 0)
            {
                placeLog(world, new BlockPos(x1, level1, z1));
                leafGen(world, x1, level1, z1);
            }

            logDirection = 0;

            index++;
        }
    }

    private void leafGen(World world, int x, int y, int z)
    {
        for (int dX = -3; dX <= 3; dX++)
        {
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
    }

    @Override
    protected int getLogMetadata()
    {
        return super.getLogMetadata() | logDirection;
    }
}
