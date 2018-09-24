package com.scottkillen.mod.dendrology.world.gen.feature.kulist;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LargeKulistTree extends NormalKulistTree
{
    public LargeKulistTree(boolean fromSapling) { super(fromSapling); }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
    	int x = pos.getX();
    	int y = pos.getY();
    	int z = pos.getZ();
        final Random rng = new Random();
        rng.setSeed(rand.nextLong());

        final int height = rng.nextInt(9) + 9;

        if (isPoorGrowthConditions(world, x, y, z, height, getSaplingBlock())) return false;

        final IBlockState state = world.getBlockState(pos.down());
        state.getBlock().onPlantGrow(state, world, pos.down(), pos);     

        for (int level = 0; level <= height; level++)
        {
            placeLog(world, new BlockPos(x, y + level, z));

            if (level == height) leafGen(world, x, y + level, z);

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

}
