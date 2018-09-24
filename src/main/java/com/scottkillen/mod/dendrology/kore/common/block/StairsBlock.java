package com.scottkillen.mod.dendrology.kore.common.block;

import net.minecraft.block.BlockStairs;

public abstract class StairsBlock extends BlockStairs
{
    protected StairsBlock(DefinesStairs model)
    {
        super(model.stairsModelBlock(), model.stairsModelSubBlockIndex());
    }

    @Override
    public boolean getUseNeighborBrightness()
    {
        // Fix lighting bugs
        return true;
    }

    @SuppressWarnings("WeakerAccess")
    protected static String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
    }

    @Override
    public final String getUnlocalizedName()
    {
        //noinspection StringConcatenationMissingWhitespace
        return "tile." + resourcePrefix() + getUnwrappedUnlocalizedName(super.getUnlocalizedName());
    }

    protected abstract String resourcePrefix();
}
