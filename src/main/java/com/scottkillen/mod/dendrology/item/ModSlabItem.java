package com.scottkillen.mod.dendrology.item;

import net.minecraft.block.Block;

import com.scottkillen.mod.dendrology.block.ModSlabBlock;
import com.scottkillen.mod.dendrology.kore.tree.item.SlabItem;

public final class ModSlabItem extends SlabItem
{
    public ModSlabItem(Block block, ModSlabBlock singleSlab, ModSlabBlock doubleSlab, Boolean isDouble)
    {
        super(block, singleSlab, doubleSlab, isDouble);
    }
}
