package com.scottkillen.mod.dendrology.kore.tree;

import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ProvidesColor
{
    @SideOnly(Side.CLIENT)
    int getLeavesInventoryColor();

    @SideOnly(Side.CLIENT)
    int getLeavesColor(IBlockAccess blockAccess, int x, int y, int z);
}
