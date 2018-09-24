package com.scottkillen.mod.dendrology.kore.tree;

import com.scottkillen.mod.dendrology.kore.common.block.StairsBlock;

import net.minecraft.block.Block;

public interface DefinesStairs
{
    void assignStairsBlock(StairsBlock block);

    StairsBlock stairsBlock();

    Block stairsModelBlock();

    int stairsModelSubBlockIndex();

    String stairsName();
}
