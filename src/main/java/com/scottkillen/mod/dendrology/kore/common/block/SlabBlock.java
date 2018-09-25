package com.scottkillen.mod.dendrology.kore.common.block;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.scottkillen.mod.dendrology.kore.common.util.slab.TheSingleSlabRegistry;
import com.scottkillen.mod.dendrology.kore.tree.DefinesSlab;

public abstract class SlabBlock extends BlockSlab
{
    public static final int CAPACITY = 8;
    private static final int METADATA_MASK = CAPACITY - 1;
    private static final TheSingleSlabRegistry slabRegistry = TheSingleSlabRegistry.REFERENCE;
    private final ImmutableList<DefinesSlab> subBlocks;

    protected SlabBlock(boolean isDouble, Collection<? extends DefinesSlab> subBlocks)
    {
        super(Material.WOOD);

        checkArgument(!subBlocks.isEmpty());
        checkArgument(subBlocks.size() <= CAPACITY);
        this.subBlocks = ImmutableList.copyOf(subBlocks);
    }

    private static int mask(int metadata) {return metadata & METADATA_MASK;}

    public static boolean isSingleSlab(Item item)
    {
        return slabRegistry.isSingleSlab(item);
    }

    protected static String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
    }

//    @SideOnly(Side.CLIENT)
//    @Override
//    public final IIcon getIcon(int side, int metadata)
//    {
//        int index = mask(metadata);
//        if (index < 0 || index >= subBlocks.size()) index = 0;
//
//        final DefinesSlab subBlock = subBlocks.get(index);
//        final Block modelBlock = subBlock.slabModelBlock();
//        final int modelBlockMetadata = subBlock.slabModelSubBlockIndex();
//        return modelBlock.getIcon(side, modelBlockMetadata);
//    }

//    @Override
//    public final Item getItemDropped(int metadata, Random unused, int unused2)
//    {
//        final DefinesSlab subBlock = subBlocks.get(mask(metadata));
//        return Item.getItemFromBlock(subBlock.singleSlabBlock());
//    }

//    @Override
//    public boolean getUseNeighborBrightness()
//    {
//        // Fix lighting bugs
//        return true;
//    }

//    @Override
//    protected final ItemStack createStackedBlock(int metadata)
//    {
//        final DefinesSlab subBlock = subBlocks.get(mask(metadata));
//        return new ItemStack(Item.getItemFromBlock(subBlock.singleSlabBlock()), 2, subBlock.slabSubBlockIndex());
//    }

    @Override
    public final String getUnlocalizedName()
    {
        return String.format("tile.%s%s", resourcePrefix(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

//    @SideOnly(Side.CLIENT)
//    @Override
//    public final void getSubBlocks(Item item, CreativeTabs unused, List subblocks)
//    {
//        if (isSingleSlab(item))
//        {
//            for (int i = 0; i < subBlocks.size(); ++i)
//            {
//                //noinspection ObjectAllocationInLoop
//                subblocks.add(new ItemStack(item, 1, i));
//            }
//        }
//    }

//    @SideOnly(Side.CLIENT)
//    @Override
//    public final void registerBlockIcons(IIconRegister unused) {}

//    @Override
//    public final String func_150002_b(int metadata)
//    {
//        int metadata1 = metadata;
//        if (metadata1 < 0 || metadata1 >= subBlocks.size())
//        {
//            metadata1 = 0;
//        }
//
//        return getUnlocalizedName() + '.' + subBlocks.get(metadata1).slabName();
//    }

    protected abstract String resourcePrefix();

    protected final List<DefinesSlab> subBlocks() { return Collections.unmodifiableList(subBlocks); }
}
