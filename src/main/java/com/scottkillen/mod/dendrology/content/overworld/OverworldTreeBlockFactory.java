package com.scottkillen.mod.dendrology.content.overworld;

import com.scottkillen.mod.dendrology.block.ModBlocks;
import com.scottkillen.mod.dendrology.block.ModLeavesBlock;
import com.scottkillen.mod.dendrology.block.ModLogBlock;
import com.scottkillen.mod.dendrology.block.ModSaplingBlock;
import com.scottkillen.mod.dendrology.block.ModSlabBlock;
import com.scottkillen.mod.dendrology.block.ModStairsBlock;
import com.scottkillen.mod.dendrology.block.ModWoodBlock;
import com.scottkillen.mod.dendrology.kore.common.block.SlabBlock;
import com.scottkillen.mod.dendrology.kore.common.block.StairsBlock;
import com.scottkillen.mod.dendrology.kore.common.util.slab.SingleDoubleSlab;
import com.scottkillen.mod.dendrology.kore.tree.DefinesLeaves;
import com.scottkillen.mod.dendrology.kore.tree.DefinesLog;
import com.scottkillen.mod.dendrology.kore.tree.DefinesSapling;
import com.scottkillen.mod.dendrology.kore.tree.DefinesSlab;
import com.scottkillen.mod.dendrology.kore.tree.DefinesStairs;
import com.scottkillen.mod.dendrology.kore.tree.DefinesWood;
import com.scottkillen.mod.dendrology.kore.tree.TreeBlockFactory;
import com.scottkillen.mod.dendrology.kore.tree.block.LeavesBlock;
import com.scottkillen.mod.dendrology.kore.tree.block.LogBlock;
import com.scottkillen.mod.dendrology.kore.tree.block.SaplingBlock;
import com.scottkillen.mod.dendrology.kore.tree.block.WoodBlock;

public final class OverworldTreeBlockFactory implements TreeBlockFactory
{

    @Override
    public LeavesBlock createLeavesBlock(Iterable<DefinesLeaves> subBlocks)
    {
        final LeavesBlock block = new ModLeavesBlock(subBlocks);
        for (final DefinesLeaves subBlock : subBlocks)
            subBlock.assignLeavesBlock(block);

        ModBlocks.registerBlock(block);
        return block;
    }

    @Override
    public LogBlock createLogBlock(Iterable<DefinesLog> subBlocks)
    {
        final LogBlock block = new ModLogBlock(subBlocks);
        for (final DefinesLog subBlock : subBlocks)
            subBlock.assignLogBlock(block);

        ModBlocks.registerBlock(block);
        return block;
    }

    @Override
    public SaplingBlock createSaplingBlock(Iterable<DefinesSapling> subBlocks)
    {
        final SaplingBlock block = new ModSaplingBlock(subBlocks);
        for (final DefinesSapling subBlock : subBlocks)
            subBlock.assignSaplingBlock(block);

        ModBlocks.registerBlock(block);
        return block;
    }

    @Override
    public SingleDoubleSlab createSlabBlocks(Iterable<DefinesSlab> subBlocks)
    {
        final SlabBlock singleSlabBlock = new ModSlabBlock(false, subBlocks);
        final SlabBlock doubleSlabBlock = new ModSlabBlock(true, subBlocks);

        for (final DefinesSlab subBlock : subBlocks)
        {
            subBlock.assignSingleSlabBlock(singleSlabBlock);
            subBlock.assignDoubleSlabBlock(doubleSlabBlock);
        }

        ModBlocks.registerBlock(singleSlabBlock, doubleSlabBlock);
        return new SingleDoubleSlab(singleSlabBlock, doubleSlabBlock);
    }

    @Override
    public StairsBlock createStairsBlock(DefinesStairs definition)
    {
        final StairsBlock block = new ModStairsBlock(definition);
        block.setUnlocalizedName(String.format("stairs.%s", definition.stairsName()));

        definition.assignStairsBlock(block);

        ModBlocks.registerBlock(block);
        return block;
    }

    @Override
    public WoodBlock createWoodBlock(Iterable<DefinesWood> subBlocks)
    {
        final WoodBlock block = new ModWoodBlock(subBlocks);
        for (final DefinesWood subBlock : subBlocks)
            subBlock.assignWoodBlock(block);

        ModBlocks.registerBlock(block);
        return block;
    }
}
