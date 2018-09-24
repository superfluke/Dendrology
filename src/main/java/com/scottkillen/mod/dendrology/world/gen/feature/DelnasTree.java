package com.scottkillen.mod.dendrology.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DelnasTree extends AbstractTree
{
    public DelnasTree(boolean fromSapling) { super(fromSapling); }

    public DelnasTree() { this(true); }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
    	int x = pos.getX();
    	int y = pos.getY();
    	int z = pos.getZ();
        final Random rng = new Random();
        rng.setSeed(rand.nextLong());

        final int height = rng.nextInt(5) + 6;

        if (isPoorGrowthConditions(world, x, y, z, height, getSaplingBlock())) return false;

        final IBlockState state = world.getBlockState(pos.down());
        state.getBlock().onPlantGrow(state, world, pos.down(), pos);

        if (rng.nextInt(10) > 0) for (int dY = 0; dY <= height; dY++)
        {
            placeLog(world, pos.add(0, dY, 0));

            if (dY == height) leafGen(world, x, y + dY, z);
        }
        else switch (rand.nextInt(4))
        {
            case 0:
                growDirect(world, rng, x, y, z, 1, 0, height);
                break;

            case 1:
                growDirect(world, rng, x, y, z, 0, 1, height);
                break;

            case 2:
                growDirect(world, rng, x, y, z, -1, 0, height);
                break;

            default:
                growDirect(world, rng, x, y, z, 0, -1, height);
        }

        return true;
    }

    private void leafGen(World world, int x, int y, int z)
    {
        for (int dX = -3; dX <= 3; dX++)
            for (int dZ = -3; dZ <= 3; dZ++)
            {
                if (Math.abs(dX) + Math.abs(dZ) <= 3 &&
                        !(Math.abs(dX) + Math.abs(dZ) == 3 && Math.abs(dX) != 0 && Math.abs(dZ) != 0))
                    placeLeaves(world, new BlockPos(x + dX, y, z + dZ));
                if (Math.abs(dX) < 2 && Math.abs(dZ) < 2 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                    placeLeaves(world, new BlockPos(x + dX, y + 1, z + dZ));
            }
    }

    private void growDirect(World world, Random rand, int x, int y, int z, int dX, int dZ, int hight)
    {
        int x1 = x;
        int z1 = z;

        placeLog(world, new BlockPos(x1, y, z1));

        if (dX == 1) placeLog(world, new BlockPos(x1 - 1, y, z1));

        if (dX == -1) placeLog(world, new BlockPos(x1 + 1, y, z1));

        if (dZ == 1) placeLog(world, new BlockPos(x1, y, z1 - 1));

        if (dZ == -1) placeLog(world, new BlockPos(x1, y, z1 + 1));

        int addlRandomLengthX = 0;
        int addlRandomLengthZ = 0;
        for (int level = 0; level <= hight; level++)
        {
            if (dX == 1 && rand.nextInt(2 + addlRandomLengthX) == 0) x1++;

            if (dX == -1 && rand.nextInt(2 + addlRandomLengthX) == 0) x1--;

            if (dZ == 1 && rand.nextInt(2 + addlRandomLengthZ) == 0) z1++;

            if (dZ == -1 && rand.nextInt(2 + addlRandomLengthZ) == 0) z1--;

            addlRandomLengthX++;
            addlRandomLengthZ++;
            placeLog(world, new BlockPos(x1, level + y, z1));
        }
        leafGen(world, x1, hight + y, z1);
    }
}
