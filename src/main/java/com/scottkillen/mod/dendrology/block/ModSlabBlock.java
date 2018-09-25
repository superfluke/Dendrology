package com.scottkillen.mod.dendrology.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.item.ItemStack;

import com.google.common.collect.ImmutableList;
import com.scottkillen.mod.dendrology.TheMod;
import com.scottkillen.mod.dendrology.kore.common.block.SlabBlock;
import com.scottkillen.mod.dendrology.kore.tree.DefinesSlab;


public final class ModSlabBlock extends SlabBlock
{
	private boolean isDouble;
	
    public ModSlabBlock(boolean isDouble, Iterable<? extends DefinesSlab> subBlocks)
    {
        super(isDouble, ImmutableList.copyOf(subBlocks));
        setCreativeTab(TheMod.INSTANCE.creativeTab());
        setHardness(2.0F);
        setResistance(5.0F);
        //setStepSound(soundTypeWood); //TODO
        this.isDouble = isDouble;
    }

    @Override
    protected String resourcePrefix() { return TheMod.getResourcePrefix(); }

	@Override
	public String getUnlocalizedName(int meta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDouble() {
		return isDouble;
	}

	@Override
	public IProperty<?> getVariantProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		// TODO Auto-generated method stub
		return null;
	}
}
