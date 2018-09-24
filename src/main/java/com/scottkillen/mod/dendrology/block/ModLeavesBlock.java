package com.scottkillen.mod.dendrology.block;

import com.google.common.collect.ImmutableList;
import com.scottkillen.mod.dendrology.TheMod;
import com.scottkillen.mod.dendrology.config.Settings;
import com.scottkillen.mod.dendrology.kore.tree.DefinesLeaves;
import com.scottkillen.mod.dendrology.kore.tree.block.LeavesBlock;


import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public final class ModLeavesBlock extends LeavesBlock
{
    public ModLeavesBlock(Iterable<? extends DefinesLeaves> subBlocks)
    {
        super(ImmutableList.copyOf(subBlocks));
        setCreativeTab(TheMod.INSTANCE.creativeTab());
    }

    @Override
    public int quantityDropped(Random random)
    {
        final int rarity = Settings.INSTANCE.saplingDropRarity();
        return rarity == 0 || random.nextInt(rarity) != 0 ? 0 : 1;
    }

    @Override
    protected String resourcePrefix() { return TheMod.getResourcePrefix(); }

	@Override
	@Nonnull
	public
	List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world,
			BlockPos pos, int fortune) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnumType getWoodType(int meta) {
		// TODO Auto-generated method stub
		return null;
	}
}
