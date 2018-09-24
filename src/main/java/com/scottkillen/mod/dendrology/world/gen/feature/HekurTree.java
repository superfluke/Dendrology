package com.scottkillen.mod.dendrology.world.gen.feature;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.google.common.base.Objects;
import com.scottkillen.mod.dendrology.kore.tree.DefinesTree;
import com.scottkillen.mod.dendrology.world.gen.feature.hekur.LargeHekurTree;
import com.scottkillen.mod.dendrology.world.gen.feature.hekur.NormalHekurTree;

public class HekurTree extends AbstractTree
{
    private final AbstractTree treeGen;
    private final AbstractTree largeTreeGen;

    public HekurTree(boolean fromSapling)
    {
        super(fromSapling);
        treeGen = new NormalHekurTree(fromSapling);
        largeTreeGen = new LargeHekurTree(fromSapling);
    }

    public HekurTree() { this(true); }

    @Override
    public void setTree(DefinesTree tree)
    {
        treeGen.setTree(tree);
        largeTreeGen.setTree(tree);
    }
    
    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
    	int x = pos.getX();
    	int y = pos.getY();
    	int z = pos.getZ();
        int y1 = y;
        while (world.getBlockState(new BlockPos(x, y1 - 1, z)).getMaterial().equals(Material.WATER)) y1--;

        if (rand.nextInt(10) < 9) return treeGen.generate(world, rand, new BlockPos(x, y1, z));

        return largeTreeGen.generate(world, rand, new BlockPos(x, y1, z));
    }
}
