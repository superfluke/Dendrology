package com.scottkillen.mod.dendrology.world.gen.feature;

import java.util.Random;

import com.google.common.base.Objects;
import com.scottkillen.mod.dendrology.kore.tree.DefinesTree;
import com.scottkillen.mod.dendrology.world.gen.feature.porffor.LargePorfforTree;
import com.scottkillen.mod.dendrology.world.gen.feature.vanilla.VanillaTree;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PorfforTree extends AbstractTree
{
    private final AbstractTree treeGen;
    private final AbstractTree largeTreeGen;

    public PorfforTree(boolean fromSapling)
    {
        super(fromSapling);
        treeGen = new VanillaTree(fromSapling);
        largeTreeGen = new LargePorfforTree(fromSapling);
    }

    public PorfforTree() { this(true); }

    @Override
    public void setTree(DefinesTree tree)
    {
        treeGen.setTree(tree);
        largeTreeGen.setTree(tree);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        if (rand.nextInt(10) < 9) return treeGen.generate(world, rand, pos);

        return largeTreeGen.generate(world, rand, pos);
    }
}
