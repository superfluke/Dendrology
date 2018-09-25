package com.scottkillen.mod.dendrology.content.crafting;

import static net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE;

import com.scottkillen.mod.dendrology.block.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public final class OreDictHandler
{
    public void registerBlocksWithOreDictinary()
    {
        for (final Block block : ModBlocks.logBlocks())
            registerWildcardOre(block, "logWood");

        for (final Block block : ModBlocks.woodBlocks())
            registerWildcardOre(block, "plankWood");

        for (final Block block : ModBlocks.singleSlabBlocks())
            registerWildcardOre(block, "slabWood");

        for (final Block block : ModBlocks.stairsBlocks())
            OreDictionary.registerOre("stairWood", block);

        for (final Block block : ModBlocks.saplingBlocks())
            registerWildcardOre(block, "treeSapling");

        for (final Block block : ModBlocks.leavesBlocks())
            registerWildcardOre(block, "treeLeaves");
    }

    private void registerWildcardOre(Block block, String name)
    {
        OreDictionary.registerOre(name, new ItemStack(block, 1, WILDCARD_VALUE));
    }
}
