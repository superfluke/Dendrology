package com.scottkillen.mod.dendrology.kore.tree.block;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.scottkillen.mod.dendrology.kore.tree.DefinesWood;

public abstract class WoodBlock extends Block
{
    public static final int CAPACITY = 16;
    private final ImmutableList<DefinesWood> subBlocks;
    private final List<IIcon> icons = Lists.newArrayList();

    protected WoodBlock(Collection<? extends DefinesWood> subBlocks)
    {
        Preconditions.checkArgument(!subBlocks.isEmpty());
        Preconditions.checkArgument(subBlocks.size() <= CAPACITY);
        this.subBlocks = ImmutableList.copyOf(subBlocks);
        //setBlockName("wood");
    }

    protected static String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
    }

    protected final List<DefinesWood> subBlocks() { return Collections.unmodifiableList(subBlocks); }

    public final ImmutableList<String> getSubBlockNames()
    {
        final List<String> names = Lists.newArrayList();
        for (final DefinesWood subBlock : subBlocks)
            names.add(subBlock.speciesName());
        return ImmutableList.copyOf(names);
    }

    @Override
    public final String getUnlocalizedName()
    {
        return String.format("tile.%s%s", resourcePrefix(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public final IIcon getIcon(int unused, int meta)
    {
        final int meta1 = meta < 0 || meta >= icons.size() ? 0 : meta;
        return icons.get(meta1);
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    @Override
    public final void getSubBlocks(Item item, CreativeTabs unused, List subblocks)
    {
        for (int i = 0; i < subBlocks.size(); i++)
            subblocks.add(new ItemStack(item, 1, i));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public final void registerBlockIcons(IIconRegister iconRegister)
    {
        icons.clear();

        for (int i = 0; i < subBlocks.size(); i++)
        {
            final String iconName = String.format("%splanks_%s", resourcePrefix(), subBlocks.get(i).speciesName());
            icons.add(i, iconRegister.registerIcon(iconName));
        }
    }

    protected abstract String resourcePrefix();
}
