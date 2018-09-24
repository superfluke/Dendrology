package com.scottkillen.mod.dendrology.kore.tree.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemSlab;

import com.scottkillen.mod.dendrology.kore.common.block.SlabBlock;

public abstract class SlabItem extends ItemSlab
{
    // This provides a reminder that you must extend this class and change the constructor to accept your extension of
    // SlabBlock in the second and third  parameters
    protected SlabItem(Block block, SlabBlock singleSlab, SlabBlock doubleSlab, Boolean isDouble)
    {
        super(block, singleSlab, doubleSlab);
    }
}
