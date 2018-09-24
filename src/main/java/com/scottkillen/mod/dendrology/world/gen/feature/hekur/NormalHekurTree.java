package com.scottkillen.mod.dendrology.world.gen.feature.hekur;


import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.scottkillen.mod.dendrology.world.gen.feature.AbstractTree;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class NormalHekurTree extends AbstractTree
{
    private int logDirection = 0;

    public NormalHekurTree(boolean fromSapling) { super(fromSapling); }

    @Override
    protected boolean isPoorGrowthConditions(World world, int x, int y, int z, int unused, IPlantable plantable)
    {
        final IBlockState state = world.getBlockState(new BlockPos(x, y - 1, z));
        return !state.getBlock().canSustainPlant(state, world, new BlockPos(x, y - 1, z), EnumFacing.UP, plantable);
    }

    @Override
    protected int getLogMetadata()
    {
        return super.getLogMetadata() | logDirection;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
    	int x = pos.getX();
    	int y = pos.getY();
    	int z = pos.getZ();
        final Random random = new Random();
        random.setSeed(rand.nextLong());

        if (isPoorGrowthConditions(world, x, y, z, 0, getSaplingBlock())) return false;

        final IBlockState state = world.getBlockState(pos.down());
        state.getBlock().onPlantGrow(state, world, pos.down(), pos);

        genRoots(world, random, x, y, z);
        growTrunk(world, random, x, y, z);

        return true;
    }

    private void genRoots(World world, Random random, int x, int y, int z)
    {
        for (final ImmutablePair<Integer, Integer> branchDirection : BRANCH_DIRECTIONS)
            if (random.nextInt(3) == 0)
                genRoot(world, random, x, y, z, branchDirection.getLeft(), branchDirection.getRight());

        genRoot(world, random, x, y, z, 0, 0);
    }

    void genRoot(World world, Random rand, int x, int y, int z, int dX, int dZ)
    {
        int x1 = x;
        int y1 = y;
        int z1 = z;

        setLogDirection(dX, dZ);

        for (int i = 0; i < 6; i++)
        {
            if (rand.nextInt(3) == 0)
            {
                if (dX == -1) x1--;

                if (dX == 1) x1++;

                if (dZ == -1) z1--;

                if (dZ == 1) z1++;
            }

            placeRoot(world, x1, y1, z1);

            y1--;
        }

        clearLogDirection();
    }

    private void setLogDirection(int dX, int dZ)
    {
        if (dX != 0) logDirection = 4;

        if (dZ != 0) logDirection = 8;
    }

    boolean canBeReplacedByRoot(World world, int x, int y, int z)
    {
        final IBlockState state = world.getBlockState(new BlockPos(x, y, z));
        final Material material = state.getMaterial();

        return canBeReplacedByLog(world, x, y, z) || material.equals(Material.SAND) || material.equals(Material.GROUND);
    }

    boolean placeRoot(World world, int x, int y, int z)
    {
        if (canBeReplacedByRoot(world, x, y, z))
        {
            setBlockAndNotifyAdequately(world, new BlockPos(x, y, z), getLogBlock().getStateFromMeta(getLogMetadata()));
            return true;
        }
        return false;
    }

    void growTrunk(World world, Random random, int x, int y, int z)
    {
    	BlockPos pos = new BlockPos(x, y, z);
        placeLog(world, pos);

        switch (random.nextInt(4))
        {
            case 0:
                placeLog(world, pos.add(0, 2, 0));
                placeLog(world, pos.add(-1, 1, 0));
                largeDirect(world, random, 1, 0, x, y + 2, z, 1, 2, 0, 2);
                break;

            case 1:
                placeLog(world, pos.add(0, 1, 0));
                placeLog(world, pos.add(0, 2, 0));
                placeLog(world, pos.add(0, 1, -1));
                largeDirect(world, random, 0, 1, x, y + 2, z, 1, 2, 0, 2);
                break;

            case 2:
                placeLog(world, pos.add(0, 1, 0));
                placeLog(world, pos.add(0, 2, 0));
                placeLog(world, pos.add(1, 1, 0));
                largeDirect(world, random, -1, 0, x, y + 2, z, 1, 2, 0, 2);
                break;

            default:
                placeLog(world, pos.add(0, 1, 0));
                placeLog(world, pos.add(0, 2, 0));
                placeLog(world, pos.add(0, 1, 1));
                largeDirect(world, random, 0, -1, x, y + 1, z, 1, 2, 0, 2);
        }
    }

    void largeDirect(World world, Random rand, int dX, int dZ, int x, int y, int z, int size, int splitCount,
                     int splitCount1, int splitCount2)
    {
        int z1 = z;
        int x1 = x;
        int y1 = y;
        setLogDirection(dX, dZ);

        int dSize = 0;

        if (size == 2) dSize = 2;

        for (int next = 0; next <= 5 * size; next++)
        {
            if (size == 1) y1++;

            placeLog(world, new BlockPos(x1, y1, z1));

            if (next <= 9 && size == 2) placeLog(world, new BlockPos(x1 - dX, y1, z1 - dZ));

            if (next == 5 * size) branchAndLeaf(world, x1, y1 + 1, z1);

            if (size == 2) y1++;

            x1 += dX;
            z1 += dZ;

            if (next == splitCount)
            {
                firstBranchSplit(world, rand, x1, y1, z1, dX, dZ, splitCount);
                secondBranchSplit(world, rand, x1, y1, z1, dX, dZ, splitCount);
            }

            if (next == 3 * size && size == 2)
            {
                fifthBranchSplit(world, rand, x1, y1, z1, dX, dZ, splitCount1);
                sixthBranchSplit(world, rand, x1, y1, z1, dX, dZ, splitCount1);
            }

            if (next == 3 * size)
            {
                thirdBranchSplit(world, rand, x1, y1, z1, dX, dZ, 4 * size - dSize);
                fourthBranchSplit(world, rand, x1, y1, z1, dX, dZ, 4 * size - dSize);
            }

            if (next == 4 * size)
            {
                fifthBranchSplit(world, rand, x1, y1, z1, dX, dZ, splitCount2);
                sixthBranchSplit(world, rand, x1, y1, z1, dX, dZ, splitCount2);
            }
        }
        clearLogDirection();
    }

    private void firstBranchSplit(World world, Random rand, int x, int y, int z, int dX, int dZ, int splitCount)
    {
        int x1 = x;
        int y1 = y;
        int z1 = z;
        for (int i = 0; i <= splitCount; i++)
        {
            if (dX != 0)
            {
                if (rand.nextInt(5) > 0) if (dX == 1) x1--;
                else x1++;
                z1 += rand.nextInt(2);
            }

            if (dZ == 1)
            {
                x1 -= rand.nextInt(2);

                if (rand.nextInt(5) > 0) z1--;
            } else if (dZ == -1)
            {
                x1 += rand.nextInt(2);

                if (rand.nextInt(5) > 0) z1++;
            }

            y1++;
            placeLog(world, new BlockPos(x1, y1, z1));

            if (i == splitCount) branchAndLeaf(world, x1, y1, z1);
        }
    }

    private void secondBranchSplit(World world, Random rand, int x, int y, int z, int dX, int dZ, int splitCount)
    {
        int x1 = x;
        int y1 = y;
        int z1 = z;
        for (int i = 0; i <= splitCount; i++)
        {
            if (dX != 0)
            {
                if (rand.nextInt(5) > 0) if (dX == 1) x1--;
                else x1++;
                z1 -= rand.nextInt(2);
            }

            if (dZ == 1)
            {
                x1 += rand.nextInt(2);

                if (rand.nextInt(5) > 0) z1--;
            } else if (dZ == -1)
            {
                x1 -= rand.nextInt(2);

                if (rand.nextInt(5) > 0) z1++;
            }

            y1++;
            placeLog(world, new BlockPos(x1, y1, z1));

            if (i == splitCount) branchAndLeaf(world, x1, y1, z1);
        }
    }

    private void thirdBranchSplit(World world, Random rand, int x, int y, int z, int dX, int dZ, int length)
    {
        int x1 = x;
        int y1 = y;
        int z1 = z;

        for (int i = 0; i <= length; i++)
        {
            if (dX != 0)
            {
                if (dX == 1) x1 += rand.nextInt(2);
                else x1 -= rand.nextInt(2);
                z1 += rand.nextInt(2);
            }

            if (dZ != 0)
            {
                if (dZ == 1) z1 += rand.nextInt(2);
                else z1 -= rand.nextInt(2);
                x1 += rand.nextInt(2);
            }

            if (i >= 3) y1 += rand.nextInt(2);

            placeLog(world, new BlockPos(x1, y1, z1));

            if (i == length) branchAndLeaf(world, x1, y1, z1);
        }
    }

    private void fourthBranchSplit(World world, Random rand, int x, int y, int z, int dX, int dZ, int length)
    {
        int x1 = x;
        int y1 = y;
        int z1 = z;

        for (int i = 0; i <= length; i++)
        {
            if (dX != 0)
            {
                if (dX == 1) x1 += rand.nextInt(2);
                else x1 -= rand.nextInt(2);
                z1 -= rand.nextInt(2);
            }

            if (dZ != 0)
            {
                if (dZ == 1) z1 += rand.nextInt(2);
                else z1 -= rand.nextInt(2);

                x1 -= rand.nextInt(2);
            }
            if (i >= 3) y1 += rand.nextInt(2);

            placeLog(world, new BlockPos(x1, y1, z1));

            if (i == length) branchAndLeaf(world, x1, y1, z1);
        }
    }

    private void fifthBranchSplit(World world, Random rand, int x, int y, int z, int dX, int dZ, int splitCount2)
    {
        int x1 = x;
        int y1 = y;
        int z1 = z;

        for (int i = 0; i <= splitCount2; i++)
        {
            if (dX == 1)
            {
                x1 -= rand.nextInt(2);
                z1 += rand.nextInt(2);
            }

            if (dX == -1)
            {
                x1 += rand.nextInt(2);
                z1 += rand.nextInt(2);
            }

            if (dZ == 1)
            {
                x1 -= rand.nextInt(2);
                z1 -= rand.nextInt(2);
            }

            if (dZ == -1)
            {
                x1 += rand.nextInt(2);
                z1 += rand.nextInt(2);
            }

            y1++;
            placeLog(world, new BlockPos(x1, y1, z1));

            if (i == splitCount2) branchAndLeaf(world, x1, y1, z1);
        }
    }

    private void sixthBranchSplit(World world, Random rand, int x, int y, int z, int dX, int dZ, int splitCount2)
    {
        int x1 = x;
        int y1 = y;
        int z1 = z;
        for (int i = 0; i <= splitCount2; i++)
        {
            if (dX != 0)
            {
                if (dX == 1)
                {
                    x1 -= rand.nextInt(2);
                } else
                {
                    x1 += rand.nextInt(2);
                }
                z1 -= rand.nextInt(2);
            }

            if (dZ == 1)
            {
                x1 += rand.nextInt(2);
                z1 -= rand.nextInt(2);
            } else if (dZ == -1)
            {
                x1 -= rand.nextInt(2);
                z1 += rand.nextInt(2);
            }

            y1++;
            placeLog(world, new BlockPos(x1, y1, z1));

            if (i == splitCount2) branchAndLeaf(world, x1, y1, z1);
        }
    }

    private void clearLogDirection() {logDirection = 0;}

    void branchAndLeaf(World world, int x, int y, int z)
    {
        placeLog(world, new BlockPos(x, y, z));

        for (int dX = -3; dX <= 3; dX++)
        {
            for (int dZ = -3; dZ <= 3; dZ++)
            {
                if ((Math.abs(dX) != 3 || Math.abs(dZ) != 3) && (Math.abs(dX) != 2 || Math.abs(dZ) != 3) &&
                        (Math.abs(dX) != 3 || Math.abs(dZ) != 2)) placeLeaves(world, new BlockPos(x + dX, y, z + dZ));

                if (Math.abs(dX) < 3 && Math.abs(dZ) < 3 && (Math.abs(dX) != 2 || Math.abs(dZ) != 2))
                    placeLeaves(world, new BlockPos(x + dX, y + 1, z + dZ));
            }
        }
    }
}
