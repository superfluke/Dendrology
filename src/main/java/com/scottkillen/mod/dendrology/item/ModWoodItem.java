package com.scottkillen.mod.dendrology.item;

import net.minecraft.block.Block;

import com.scottkillen.mod.dendrology.block.ModWoodBlock;
import com.scottkillen.mod.dendrology.kore.tree.item.WoodItem;

public final class ModWoodItem extends WoodItem
{
    public ModWoodItem(Block block, ModWoodBlock log, String[] names)
    {
        super(block, log, names);
    }
}
