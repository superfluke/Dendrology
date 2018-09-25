package com.scottkillen.mod.dendrology.content.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

import com.scottkillen.mod.dendrology.block.ModBlocks;
import com.scottkillen.mod.dendrology.kore.tree.DefinesLog;
import com.scottkillen.mod.dendrology.kore.tree.DefinesSlab;
import com.scottkillen.mod.dendrology.kore.tree.DefinesStairs;

public final class Crafter
{
    public void writeRecipes()
    {
        initLogRecipes();
        initPlankRecipes();
        initSaplingRecipes();
    }

    private void initSaplingRecipes()
    { //TODO
//        CraftingManager.getInstance().addShapelessRecipe(new ItemStack(Items.dye, 1, 9),
//                new ItemStack(OverworldTreeSpecies.CERASU.saplingBlock(), 1,
//                        OverworldTreeSpecies.CERASU.saplingSubBlockIndex()));
//
//        CraftingManager.getInstance().addShapelessRecipe(new ItemStack(Items.dye, 1, 10),
//                new ItemStack(OverworldTreeSpecies.EWCALY.saplingBlock(), 1,
//                        OverworldTreeSpecies.EWCALY.saplingSubBlockIndex()));
//
//        CraftingManager.getInstance().addShapelessRecipe(new ItemStack(Items.dye, 1, 5),
//                new ItemStack(OverworldTreeSpecies.PORFFOR.saplingBlock(), 1,
//                        OverworldTreeSpecies.PORFFOR.saplingSubBlockIndex()));
//
//        CraftingManager.getInstance().addShapelessRecipe(new ItemStack(Items.dye, 1, 14),
//                new ItemStack(OverworldTreeSpecies.ACEMUS.saplingBlock(), 1,
//                        OverworldTreeSpecies.ACEMUS.saplingSubBlockIndex()));
    }

    private void initLogRecipes()
    {
//        for (final DefinesLog definition : ModBlocks.logDefinitions())
//            CraftingManager.getInstance()
//                    .addRecipe(new ItemStack(definition.woodBlock(), 4, definition.woodSubBlockIndex()), "#", '#',
//                            new ItemStack(definition.logBlock(), 1, definition.logSubBlockIndex()));
    }

    private void initPlankRecipes()
    {
        initWoodStairsRecipes();
        initWoodSlabRecipes();
    }

    private void initWoodStairsRecipes() //TODO jaysun recipes
    {
//        for (final DefinesStairs definition : ModBlocks.stairsDefinitions())
//            CraftingManager.getInstance()
//                    .addRecipe(new ItemStack(definition.stairsBlock(), 4), "#  ", "## ", "###", '#',
//                            new ItemStack(definition.stairsModelBlock(), 1, definition.stairsModelSubBlockIndex()));
    }

    private void initWoodSlabRecipes()
    {
//        for (final DefinesSlab definition : ModBlocks.slabDefinitions())
//            CraftingManager.getInstance()
//                    .addRecipe(new ItemStack(definition.singleSlabBlock(), 6, definition.slabSubBlockIndex()), "###",
//                            '#', new ItemStack(definition.slabModelBlock(), 1, definition.slabSubBlockIndex()));
    }
}
