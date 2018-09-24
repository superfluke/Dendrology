package com.scottkillen.mod.dendrology.world.gen.feature;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class KiparisTree extends AbstractTree
{
    public KiparisTree(boolean fromSapling) { super(fromSapling); }

    public KiparisTree() { this(true); }

    @Override
    protected boolean canBeReplacedByLog(World world, int x, int y, int z)
    {
        return super.canBeReplacedByLog(world, x, y, z) || world.getBlockState(new BlockPos(x, y, z)).getMaterial().equals(Material.WATER);
    }

    @Override
	public boolean isReplaceable(World world, BlockPos pos)
    {
        return super.isReplaceable(world, pos) || world.getBlockState(pos).getMaterial().equals(Material.WATER);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
    	int x = pos.getX();
    	int y = pos.getY();
    	int z = pos.getZ();
        final Random rng = new Random();
        rng.setSeed(rand.nextLong());

        final int size =
                1 + (rng.nextInt(7) < 2 ? 1 : 0) + (rng.nextInt(7) < 2 ? 1 : 0) + (rng.nextInt(2) == 0 ? 1 : 0);
        final int height = 4 * size + 1;

        if (isPoorGrowthConditions(world, x, y, z, height, getSaplingBlock())) return false;

        final IBlockState state = world.getBlockState(pos.down());
        state.getBlock().onPlantGrow(state, world, pos.down(), pos);

        for (int dY = 0; dY <= height; dY++)
        {
            if (dY != height) placeLog(world, new BlockPos(x, y + dY, z));

            if (dY >= 1)
            {
                switch (size)
                {
                    case 1:
                        genSmallLeaves(world, x, y + dY, z);
                        break;
                    case 2:
                        genMediumLeaves(world, x, y, z, dY);
                        break;
                    case 3:
                        genLargeLeaves(world, x, y, z, dY);
                        break;
                    default:
                        genExtraLargeLeaves(world, x, y, z, dY);
                        break;
                }
            }

            if (dY == height) placeLeaves(world, new BlockPos(x, y + dY + 1, z));
            if (dY == height && (size == 4 || size == 3)) placeLeaves(world, new BlockPos(x, y + dY + 2, z));
        }
        return true;
    }

    private void genExtraLargeLeaves(World world, int x, int y, int z, int dY)
    {
        for (int dX = -3; dX <= 3; dX++)
            for (int dZ = -3; dZ <= 3; dZ++)
            {
                if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                    placeLeaves(world, new BlockPos(x + dX, y + dY, z + dZ));

                if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && dY <= 14 && dY >= 2)
                    placeLeaves(world, new BlockPos(x + dX, y + dY, z + dZ));

                if (Math.abs(dX) <= 2 && Math.abs(dZ) <= 2 && (Math.abs(dX) != 2 || Math.abs(dZ) != 2) && dY == 12 ||
                        dY == 11 || dY == 3) placeLeaves(world, new BlockPos(x + dX, y + dY, z + dZ));

                if ((Math.abs(dX) != 3 || Math.abs(dZ) != 3) && (Math.abs(dX) != 3 || Math.abs(dZ) != 2) &&
                        (Math.abs(dX) != 2 || Math.abs(dZ) != 3) && dY <= 10 && dY >= 4)
                    placeLeaves(world, new BlockPos(x + dX, y + dY, z + dZ));
            }
    }

    private void genLargeLeaves(World world, int x, int y, int z, int dY)
    {
        for (int dX = -2; dX <= 2; dX++)
            for (int dZ = -2; dZ <= 2; dZ++)
            {
                if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                    placeLeaves(world, new BlockPos(x + dX, y + dY, z + dZ));

                if ((Math.abs(dX) != 2 || Math.abs(dZ) != 2) && (Math.abs(dX) != 2 || Math.abs(dZ) != 1) &&
                        (Math.abs(dX) != 1 || Math.abs(dZ) != 2) && dY <= 10 && dY >= 2)
                    placeLeaves(world, new BlockPos(x + dX, y + dY, z + dZ));
            }
    }

    private void genMediumLeaves(World world, int x, int y, int z, int dY)
    {
        for (int dX = -2; dX <= 2; dX++)
            for (int dZ = -2; dZ <= 2; dZ++)
            {
                if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                {
                    placeLeaves(world, new BlockPos(x + dX, y + dY, z + dZ));
                }
                if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && dY == 7)
                {
                    placeLeaves(world, new BlockPos(x + dX, y + 7, z + dZ));
                }
                if ((Math.abs(dX) != 2 || Math.abs(dZ) != 2) && (Math.abs(dX) != 2 || Math.abs(dZ) != 1) &&
                        (Math.abs(dX) != 1 || Math.abs(dZ) != 2) && dY <= 6 && dY >= 2)
                {
                    placeLeaves(world, new BlockPos(x + dX, y + dY, z + dZ));
                }
            }
    }

    private void genSmallLeaves(World world, int x, int y, int z)
    {
        for (int dX = -1; dX <= 1; dX++)
            for (int dZ = -1; dZ <= 1; dZ++)
                if (Math.abs(dX) != 1 || Math.abs(dZ) != 1) placeLeaves(world, new BlockPos(x + dX, y, z + dZ));
    }
}
