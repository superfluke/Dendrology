package com.scottkillen.mod.dendrology.kore.common.util.slab;

import com.google.common.base.Objects;
import com.scottkillen.mod.dendrology.kore.common.block.SlabBlock;

import org.apache.commons.lang3.tuple.ImmutablePair;

public final class SingleDoubleSlab
{
    private final ImmutablePair<SlabBlock, SlabBlock> pair;

    public SingleDoubleSlab(SlabBlock singleSlab, SlabBlock doubleSlab)
    {
        pair = ImmutablePair.of(singleSlab, doubleSlab);
    }

    public SlabBlock singleSlab()
    {
        return pair.left;
    }

    public SlabBlock doubleSlab()
    {
        return pair.right;
    }
}
