package com.scottkillen.mod.dendrology.kore.tree.block;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockSapling;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.scottkillen.mod.dendrology.kore.tree.DefinesSapling;

public abstract class SaplingBlock extends BlockSapling
{
    public static final int CAPACITY = 8;
    private static final int METADATA_MASK = CAPACITY - 1;
    private final ImmutableList<DefinesSapling> subBlocks;
    private final List<IIcon> subblockIcons;

    protected SaplingBlock(Collection<? extends DefinesSapling> subBlocks)
    {
        checkArgument(!subBlocks.isEmpty());
        checkArgument(subBlocks.size() <= CAPACITY);
        this.subBlocks = ImmutableList.copyOf(subBlocks);

        subblockIcons = Lists.newArrayListWithCapacity(subBlocks.size());

        //setBlockName("sapling");
    }

    protected static String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
    }

    private static int mask(int metadata) { return metadata & METADATA_MASK; }

    protected final List<DefinesSapling> subBlocks() { return Collections.unmodifiableList(subBlocks); }

    public final List<String> subBlockNames()
    {
        final List<String> names = Lists.newArrayListWithCapacity(subBlocks.size());
        for (final DefinesSapling subBlock : subBlocks)
            names.add(subBlock.speciesName());
        return ImmutableList.copyOf(names);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public final IIcon getIcon(int unused, int metadata)
    {
        int index = mask(metadata);
        if (index < 0 || index >= subblockIcons.size()) index = 0;
        return subblockIcons.get(index);
    }

    @Override
    public final void func_149878_d(World world, int x, int y, int z, Random rand)
    {
        if (!TerrainGen.saplingGrowTree(world, rand, x, y, z)) return;

        final int metadata = mask(world.getBlockMetadata(x, y, z));
        final WorldGenerator treeGen = subBlocks.get(metadata).saplingTreeGenerator();
        world.setBlock(x, y, z, Blocks.air, 0, 4);
        if (!treeGen.generate(world, rand, x, y, z)) world.setBlock(x, y, z, this, metadata, 4);
    }

    @Override
    public final int damageDropped(int metadata)
    {
        return mask(metadata);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public final void getSubBlocks(Item item, CreativeTabs unused, List subBlocks)
    {
        for (int i = 0; i < this.subBlocks.size(); i++)
            //noinspection ObjectAllocationInLoop
            subBlocks.add(new ItemStack(item, 1, i));
    }

    @Override
    public final void registerBlockIcons(IIconRegister iconRegister)
    {
        subblockIcons.clear();

        for (int i = 0; i < subBlocks.size(); i++)
        {
            final String iconName = String.format("%ssapling_%s", resourcePrefix(), subBlocks.get(i).speciesName());
            subblockIcons.add(i, iconRegister.registerIcon(iconName));
        }
    }

    @Override
    public final String getUnlocalizedName()
    {
        return String.format("tile.%s%s", resourcePrefix(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected abstract String resourcePrefix();
}
