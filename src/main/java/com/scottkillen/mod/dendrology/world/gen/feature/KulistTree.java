package com.scottkillen.mod.dendrology.world.gen.feature;

import java.util.Random;

import com.google.common.base.Objects;
import com.scottkillen.mod.dendrology.kore.tree.DefinesTree;
import com.scottkillen.mod.dendrology.world.gen.feature.kulist.LargeKulistTree;
import com.scottkillen.mod.dendrology.world.gen.feature.kulist.NormalKulistTree;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class KulistTree extends AbstractTree
{
    private final AbstractTree treeGen;
    private final AbstractTree largeTreeGen;

    public KulistTree(boolean fromSapling)
    {
        super(fromSapling);
        treeGen = new NormalKulistTree(fromSapling);
        largeTreeGen = new LargeKulistTree(fromSapling);
    }

    public KulistTree() { this(true); }

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
