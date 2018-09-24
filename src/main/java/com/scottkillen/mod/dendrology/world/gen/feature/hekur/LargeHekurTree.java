package com.scottkillen.mod.dendrology.world.gen.feature.hekur;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LargeHekurTree extends NormalHekurTree
{
    public LargeHekurTree(boolean fromSapling) { super(fromSapling); }

    @Override
    protected void growTrunk(World world, Random random, int x, int y, int z)
    {
    	BlockPos pos = new BlockPos(x, y, z);
        placeLog(world, pos);

        switch (random.nextInt(4))
        {
            case 0:
                placeLog(world, pos.add(1, 0, 0));
                placeLog(world, pos.add(1, 1, 0));
                largeDirect(world, random, 1, 0, x, y + 1, z, 2, 5, 4, 3);
                break;

            case 1:
                placeLog(world, pos.add(0, 0, 1));
                placeLog(world, pos.add(0, 1, 1));
                largeDirect(world, random, 0, 1, x, y + 1, z, 2, 5, 4, 3);
                break;

            case 2:
            	placeLog(world, pos.add(-1, 0, 0));
            	placeLog(world, pos.add(-1, 1, 0));
                largeDirect(world, random, -1, 0, x, y + 1, z, 2, 5, 4, 3);
                break;

            default:
            	placeLog(world, pos.add(0, 0, -1));
                placeLog(world, pos.add(0, 1, -1));
                largeDirect(world, random, 0, -1, x, y + 1, z, 2, 5, 4, 3);
        }
    }
}
