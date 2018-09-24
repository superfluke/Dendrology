package com.scottkillen.mod.dendrology.world.gen.feature;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.google.common.base.Objects;
import com.scottkillen.mod.dendrology.kore.tree.DefinesTree;
import com.scottkillen.mod.dendrology.world.gen.feature.ewcaly.LargeEwcalyTree;
import com.scottkillen.mod.dendrology.world.gen.feature.ewcaly.NormalEwcalyTree;

public class EwcalyTree extends AbstractTree
{
    private final AbstractTree treeGen;
    private final AbstractTree largeTreeGen;

    public EwcalyTree(boolean fromSapling)
    {
        super(fromSapling);
        treeGen = new NormalEwcalyTree(fromSapling);
        largeTreeGen = new LargeEwcalyTree(fromSapling);
    }

    public EwcalyTree() { this(true); }

    @Override
    public void setTree(DefinesTree tree)
    {
        treeGen.setTree(tree);
        largeTreeGen.setTree(tree);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        if (rand.nextInt(7) > 1) return treeGen.generate(world, rand, pos);

        return largeTreeGen.generate(world, rand, pos);
    }
}
