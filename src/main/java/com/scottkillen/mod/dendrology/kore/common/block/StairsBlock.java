package com.scottkillen.mod.dendrology.kore.common.block;

import net.minecraft.block.BlockStairs;

import com.scottkillen.mod.dendrology.kore.tree.DefinesStairs;

public abstract class StairsBlock extends BlockStairs
{
    protected StairsBlock(DefinesStairs model)
    {
        super(model.stairsModelBlock().getStateFromMeta(model.stairsModelSubBlockIndex()));
    }



    protected static String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
    }

    @Override
    public final String getUnlocalizedName()
    {
        return "tile." + resourcePrefix() + getUnwrappedUnlocalizedName(super.getUnlocalizedName());
    }

    protected abstract String resourcePrefix();
}
