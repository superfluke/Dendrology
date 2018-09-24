package com.scottkillen.mod.dendrology.content.fuel;

import com.scottkillen.mod.dendrology.kore.common.block.SlabBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

public enum FuelHandler implements IFuelHandler
{
    INSTANCE;

    public static void postInit()
    {
        GameRegistry.registerFuelHandler(INSTANCE); //TODO
    }

    @Override
    public int getBurnTime(ItemStack fuel)
    {
        final Item fuelItem = fuel.getItem();
        final IBlockState state = Block.getBlockFromItem(fuelItem).getBlockState().getBaseState(); //I assume since I'm not getting an error here that this is 100% the correct way to do it
        final Material fuelMaterial = state.getMaterial();
        if (fuelMaterial.equals(Material.WOOD) && SlabBlock.isSingleSlab(fuelItem)) return 150;

        return 0;
    }
}
