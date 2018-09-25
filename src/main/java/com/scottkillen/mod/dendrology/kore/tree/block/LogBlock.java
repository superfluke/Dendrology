package com.scottkillen.mod.dendrology.kore.tree.block;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.BlockLog;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.scottkillen.mod.dendrology.kore.tree.DefinesLog;

public abstract class LogBlock extends BlockLog
{
    public static final int CAPACITY = 4;
    private final ImmutableList<DefinesLog> subBlocks;

    protected LogBlock(Collection<? extends DefinesLog> subBlocks)
    {
        checkArgument(!subBlocks.isEmpty());
        checkArgument(subBlocks.size() <= CAPACITY);
        this.subBlocks = ImmutableList.copyOf(subBlocks);
        //setBlockName("log");
    }

    protected static String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
    }

    protected final List<DefinesLog> subBlocks() { return Collections.unmodifiableList(subBlocks); }

    public final ImmutableList<String> getSubBlockNames()
    {
        final List<String> names = Lists.newArrayList();
        for (final DefinesLog subBlock : subBlocks)
            names.add(subBlock.speciesName());
        return ImmutableList.copyOf(names);
    }

    @Override
    public final String getUnlocalizedName()
    {
        return String.format("tile.%s%s", resourcePrefix(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

//    @SuppressWarnings("unchecked")
//    @SideOnly(Side.CLIENT)
//    @Override
//    public final void getSubBlocks(Item item, CreativeTabs unused, List subblocks)
//    {
//        for (int i = 0; i < subBlocks.size(); i++)
//            subblocks.add(new ItemStack(item, 1, i));
//    }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public final void registerBlockIcons(IIconRegister iconRegister)
//    {
//        field_150167_a = new IIcon[subBlocks.size()];
//        field_150166_b = new IIcon[subBlocks.size()];
//
//        for (int i = 0; i < subBlocks.size(); i++)
//        {
//            final String iconName = String.format("%slog_%s", resourcePrefix(), subBlocks.get(i).speciesName());
//            field_150167_a[i] = iconRegister.registerIcon(iconName);
//            field_150166_b[i] = iconRegister.registerIcon(iconName + "_top");
//        }
//    }

    protected abstract String resourcePrefix();
}
