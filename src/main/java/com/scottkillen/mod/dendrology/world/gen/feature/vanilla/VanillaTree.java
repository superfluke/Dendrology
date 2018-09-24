package com.scottkillen.mod.dendrology.world.gen.feature.vanilla;

import java.util.Random;

import com.scottkillen.mod.dendrology.world.gen.feature.AbstractTree;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VanillaTree extends AbstractTree
{
    public VanillaTree(boolean fromSapling) { super(fromSapling); }

    @Override
    protected boolean hasRoomToGrow(World world, int x, int y, int z, int height)
    {
        for (int y1 = y; y1 <= y + 1 + height; ++y1)
        {
            final int radius = y1 >= y + 1 + height - 2 ? 2 : y1 == y ? 0 : 1;
            for (int x1 = x - radius; x1 <= x + radius; ++x1)
                for (int z1 = z - radius; z1 <= z + radius; ++z1)
                    if (y1 >= 0 && y1 < 256)
                    {
                        if (!isReplaceable(world, new BlockPos(x1, y1, z1))) return false;
                    } else return false;
        }
        return true;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
    	int x = pos.getX();
    	int y = pos.getY();
    	int z = pos.getZ();
        final int height = 4 + rand.nextInt(3) + rand.nextInt(7);

        if (isPoorGrowthConditions(world, x, y, z, height, getSaplingBlock())) return false;

        final IBlockState state = world.getBlockState(pos.down());
        state.getBlock().onPlantGrow(state, world, pos.down(), pos);     

        placeCanopy(world, rand, x, y, z, height);

        for (int dY = 0; dY < height; ++dY)
            placeLog(world, new BlockPos(x, y + dY, z));

        return true;
    }

    private void placeCanopy(World world, Random rand, int x, int y, int z, int height)
    {
        for (int y1 = y - 3 + height; y1 <= y + height; ++y1)
        {
            final int distanceToTopOfTrunk = y1 - (y + height);
            final int radius = 1 - distanceToTopOfTrunk / 2;

            for (int x1 = x - radius; x1 <= x + radius; ++x1)
            {
                final int dX = x1 - x;

                for (int z1 = z - radius; z1 <= z + radius; ++z1)
                {
                    final int dZ = z1 - z;

                    if (Math.abs(dX) != radius || Math.abs(dZ) != radius ||
                            rand.nextInt(2) != 0 && distanceToTopOfTrunk != 0) placeLeaves(world, new BlockPos(x1, y1, z1));
                }
            }
        }
    }
}
