package com.scottkillen.mod.dendrology.kore.common.util.slab;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import com.scottkillen.mod.dendrology.kore.common.block.SlabBlock;

public enum TheSingleSlabRegistry
{
    REFERENCE;

    private final Set<Block> slabBlocks = Sets.newHashSet();

    public void add(SlabBlock slabBlock) { slabBlocks.add(checkNotNull(slabBlock)); }

    public boolean isSingleSlab(Block block) { return slabBlocks.contains(block); }

    public boolean isSingleSlab(Item item) { return isSingleSlab(Block.getBlockFromItem(item)); }
}
