package com.scottkillen.mod.dendrology.world.gen.feature.cedrum;

import java.util.Random;

import com.scottkillen.mod.dendrology.world.gen.feature.AbstractTree;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NormalCedrumTree extends AbstractTree
{
    int logDirection = 0;

    public NormalCedrumTree(boolean fromSapling) { super(fromSapling); }

    @Override
    protected boolean canBeReplacedByLog(World world, int x, int y, int z)
    {
        return super.canBeReplacedByLog(world, x, y, z) || world.getBlockState(new BlockPos(x, y, z)).getMaterial().equals(Material.WATER);
    }

    @Override
    protected int getLogMetadata() {return super.getLogMetadata() | logDirection;}

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
        final int height = rand.nextInt(10) + 9;

        if (isPoorGrowthConditions(world, x, y, z, height, getSaplingBlock())) return false;

        final IBlockState state = world.getBlockState(pos.down());
        state.getBlock().onPlantGrow(state, world, pos.down(), pos);  

        for (int level = 0; level <= height; level++)
        {
            placeLog(world, new BlockPos(x, y + level, z));

            if (level == height) leafTop(world, x, y + level, z);

            if (level > 5 && level < height)
            {
                if (level == height - 1)
                {
                    leafGen(world, 2, x, y + level, z);
                }

                if (level == height - 4 || level == height - 7)
                {
                    for (int next = 1; next < 3; next++)
                    {
                        logDirection = 4;
                        placeLog(world, new BlockPos(x + next, y + level - 2, z));
                        placeLog(world, new BlockPos(x - next, y + level - 2, z));
                        logDirection = 8;
                        placeLog(world, new BlockPos(x, y + level - 2, z + next));
                        placeLog(world, new BlockPos(x, y + level - 2, z - next));
                        logDirection = 0;
                    }
                    leafGen(world, level == height - 4 ? 3 : 4, x, y + level, z);
                }

                if (level == height - 10 || level == height - 13)
                {
                    leafGen(world, level == height - 10 ? 3 : 2, x, y + level, z);
                }
            }
        }

        return true;
    }

    void leafTop(World world, int x, int y, int z)
    {
        for (int dX = -2; dX <= 2; dX++)
            for (int dZ = -2; dZ <= 2; dZ++)
            {
                if (Math.abs(dX) + Math.abs(dZ) < 3) placeLeaves(world, new BlockPos(x + dX, y, z + dZ));

                if (Math.abs(dX) + Math.abs(dZ) < 2) placeLeaves(world, new BlockPos(x + dX, y + 1, z + dZ));

                if (Math.abs(dX) == 0 && Math.abs(dZ) == 0) placeLeaves(world, new BlockPos(x + dX, y + 2, z + dZ));
            }
    }

    void leafGen(World world, int size, int x, int y, int z)
    {
        final int radius;
        final int limiter1;
        final int limiter2;
        final int limiter3;

        switch (size)
        {
            case 3:
                radius = 4;
                limiter1 = 3;
                limiter2 = 5;
                limiter3 = 7;
                break;
            case 4:
                radius = 5;
                limiter1 = 5;
                limiter2 = 7;
                limiter3 = 8;
                break;
            case 5:
                radius = 6;
                limiter1 = 7;
                limiter2 = 8;
                limiter3 = 9;
                break;
            default:
                radius = 3;
                limiter1 = 2;
                limiter2 = 3;
                limiter3 = 5;
        }
        doLeafGen(world, x, y, z, radius, limiter1, limiter2, limiter3);
    }

    private void doLeafGen(World world, int x, int y, int z, int radius, int limiter1, int limiter2, int limiter3)
    {
        for (int dX = -radius; dX <= radius; dX++)
            for (int dZ = -radius; dZ <= radius; dZ++)
            {
                if (Math.abs(dX) + Math.abs(dZ) < limiter1) placeLeaves(world, new BlockPos(x + dX, y, z + dZ));

                if (Math.abs(dX) + Math.abs(dZ) < limiter2) placeLeaves(world, new BlockPos(x + dX, y - 1, z + dZ));

                if (Math.abs(dX) + Math.abs(dZ) < limiter3) placeLeaves(world, new BlockPos(x + dX, y - 2, z + dZ));
            }
    }
}
