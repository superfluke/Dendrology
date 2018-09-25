package com.scottkillen.mod.dendrology.content.crafting;

import com.scottkillen.mod.dendrology.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Smelter
{
    public void registerSmeltings()
    {
        for (final Block log : ModBlocks.logBlocks())
        {
            registerCharcoalSmelting(log);
        }
    }

    private static void registerCharcoalSmelting(Block log)
    {
    	GameRegistry.addSmelting(log, new ItemStack(Items.COAL, 1, 1), 0.15F);
    }
}
