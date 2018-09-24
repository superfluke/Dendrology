package com.scottkillen.mod.dendrology.world.gen.feature.ewcaly;

import java.util.Random;

import com.google.common.base.Objects;
import com.scottkillen.mod.dendrology.world.gen.feature.AbstractTree;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NormalEwcalyTree extends AbstractTree
{
    private int logDirection = 0;

    public NormalEwcalyTree(boolean fromSapling) { super(fromSapling); }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
    	int x = pos.getX();
    	int y = pos.getY();
    	int z = pos.getZ();
        final Random rng = new Random();
        rng.setSeed(rand.nextLong());

        final int height = rng.nextInt(24) + 2;

        if (isPoorGrowthConditions(world, x, y, z, height, getSaplingBlock())) return false;

        final IBlockState state = world.getBlockState(pos.down());
        state.getBlock().onPlantGrow(state, world, pos.down(), pos);  

        for (int dY = 0; dY <= height; dY++)
            placeLog(world, new BlockPos(x, y + dY, z));

        int size = 1;

        for (int y1 = y + height / 2; y1 <= y + height; y1++)
            if (rng.nextInt(5) > 2 || y1 == y + height)
            {
                if (rng.nextInt(20) < 1) size = 2;

                if (rng.nextInt(4) == 0 && y1 - y > 10 && y1 - y < 20) size = 2;

                if (y1 - y >= 20) size = 3;

                for (int dX = -size; dX <= size; dX++)
                    for (int dZ = -size; dZ <= size; dZ++)
                    {
                        placeLeaves(world, new BlockPos(x + dX, y1, z + dZ));

                        if (size == 3 &&
                                (Math.abs(dX) == 3 && Math.abs(dZ) == 2 || Math.abs(dX) == 2 && Math.abs(dZ) == 3))
                        {
                            setBlockAndNotifyAdequately(world, new BlockPos(x + dX, y1, z + dZ), Blocks.AIR.getDefaultState());
                        }

                        if (y1 == y + height && Math.abs(dX) < 3 && Math.abs(dZ) < 3 &&
                                (Math.abs(dX) != 2 || Math.abs(dZ) != 2))
                        {
                            if (size > 1)
                            {
                                placeLeaves(world, new BlockPos(x + dX, y1 + 1, z + dZ));
                            }

                            if (size == 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                            {
                                placeLeaves(world, new BlockPos(x + dX, y1 + 1, z + dZ));
                            }
                        }
                    }
            }

        for (int dY = height / 2; dY <= height - 3; dY++)
        {
            if (rng.nextInt(11) == 0)
            {
                branches(world, rng, x, y + dY, z, -1, 0, height);
            }

            if (rng.nextInt(11) == 0)
            {
                branches(world, rng, x, y + dY, z, 1, 0, height);
            }

            if (rng.nextInt(11) == 0)
            {
                branches(world, rng, x, y + dY, z, 0, -1, height);
            }

            if (rng.nextInt(11) == 0)
            {
                branches(world, rng, x, y + dY, z, 0, 1, height);
            }

            if (rng.nextInt(11) == 0)
            {
                branches(world, rng, x, y + dY, z, -1, 1, height);
            }

            if (rng.nextInt(11) == 0)
            {
                branches(world, rng, x, y + dY, z, -1, -1, height);
            }

            if (rng.nextInt(11) == 0)
            {
                branches(world, rng, x, y + dY, z, 1, 1, height);
            }

            if (rng.nextInt(11) == 0)
            {
                branches(world, rng, x, y + dY, z, 1, -1, height);
            }
        }

        return true;
    }

    private void branches(World world, Random rand, int x, int y, int z, int dX, int dZ, int height)
    {
        int x1 = x;
        int y1 = y;
        int z1 = z;

        for (int i = 0; i < 5; i++)
        {
            if (dX == -1 && rand.nextInt(3) == 0)
            {
                x1--;
                logDirection = 4;
            }

            if (dX == 1 && rand.nextInt(3) == 0)
            {
                x1++;
                logDirection = 4;
            }

            if (dZ == -1 && rand.nextInt(3) == 0)
            {
                z1--;
                logDirection = 8;
            }

            if (dZ == 1 && rand.nextInt(3) == 0)
            {
                z1++;
                logDirection = 8;
            }

            placeLog(world, new BlockPos(x1, y1, z1));
            logDirection = 0;

            if (i == 4 && height >= 18)
            {
                genLeaves(world, x1, y1, z1);
            }

            if (i == 4 && height < 18)
            {
                genLeavesS(world, x1, y1, z1);
            }

            y1++;
        }
    }

    private void genLeaves(World world, int x, int y, int z)
    {
        for (int dX = -3; dX <= 3; dX++)
            for (int dZ = -3; dZ <= 3; dZ++)
            {
                if ((Math.abs(dX) != 3 || Math.abs(dZ) != 3) && (Math.abs(dX) != 2 || Math.abs(dZ) != 3) &&
                        (Math.abs(dX) != 3 || Math.abs(dZ) != 2)) placeLeaves(world, new BlockPos(x + dX, y, z + dZ));

                if (Math.abs(dX) < 3 && Math.abs(dZ) < 3 && (Math.abs(dX) != 2 || Math.abs(dZ) != 2))
                {
                    placeLeaves(world, new BlockPos(x + dX, y - 1, z + dZ));
                    placeLeaves(world, new BlockPos(x + dX, y + 1, z + dZ));
                }
            }
    }

    private void genLeavesS(World world, int x, int y, int z)
    {
        for (int dX = -2; dX <= 2; dX++)
            for (int dZ = -2; dZ <= 2; dZ++)
            {
                if (Math.abs(dX) != 2 || Math.abs(dZ) != 2) placeLeaves(world, new BlockPos(x + dX, y, z + dZ));

                if (Math.abs(dX) < 2 && Math.abs(dZ) < 2 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                {
                    placeLeaves(world, new BlockPos(x + dX, y + 1, z + dZ));
                    placeLeaves(world, new BlockPos(x + dX, y - 1, z + dZ));
                }
            }
    }

    @Override
    protected int getLogMetadata() { return super.getLogMetadata() | logDirection; }
}
