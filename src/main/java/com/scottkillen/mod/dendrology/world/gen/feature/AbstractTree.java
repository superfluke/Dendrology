package com.scottkillen.mod.dendrology.world.gen.feature;

import static com.google.common.base.Preconditions.checkArgument;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.scottkillen.mod.dendrology.kore.tree.DefinesTree;
import com.scottkillen.mod.dendrology.kore.tree.block.LeavesBlock;
import com.scottkillen.mod.dendrology.kore.tree.block.LogBlock;
import com.scottkillen.mod.dendrology.kore.tree.block.SaplingBlock;

public abstract class AbstractTree extends WorldGenAbstractTree
{
    protected static final ImmutableList<ImmutablePair<Integer, Integer>> BRANCH_DIRECTIONS = ImmutableList
            .of(ImmutablePair.of(-1, 0), ImmutablePair.of(1, 0), ImmutablePair.of(0, -1), ImmutablePair.of(0, 1),
                    ImmutablePair.of(-1, 1), ImmutablePair.of(-1, -1), ImmutablePair.of(1, 1), ImmutablePair.of(1, -1));
    private DefinesTree tree = null;

    protected AbstractTree(boolean fromSapling) { super(fromSapling); }

    protected boolean canBeReplacedByLog(World world, int x, int y, int z)
    {
        final IBlockState state = world.getBlockState(new BlockPos(x, y, z));

        return state.getBlock().isAir(state, world, new BlockPos(x, y, z)) || state.getBlock().isLeaves(state, world, new BlockPos(x, y, z));
    }

    public void setTree(DefinesTree tree)
    {
        this.tree = tree;
    }

    protected boolean isPoorGrowthConditions(World world, int x, int y, int z, int height, IPlantable plantable)
    {
    	Preconditions.checkArgument(height > 0);
        if (y < 1 || y + height + 1 > world.getHeight()) return true;
        if (!hasRoomToGrow(world, x, y, z, height)) return true;

        final IBlockState soil = world.getBlockState(new BlockPos(x, y - 1, z));
        return !soil.getBlock().canSustainPlant(soil, world, new BlockPos(x, y - 1, z), EnumFacing.UP, plantable);
    }

    protected boolean hasRoomToGrow(World world, int x, int y, int z, int height)
    {
        for (int y1 = y; y1 <= y + 1 + height; ++y1)
            if (!isReplaceable(world, new BlockPos(x, y1, z))) return false;

        return true;
    }

    LeavesBlock getLeavesBlock() { return tree.leavesBlock(); }

    int getLeavesMetadata() { return tree.leavesSubBlockIndex(); }

    protected LogBlock getLogBlock() { return tree.logBlock(); }

    protected int getLogMetadata() { return tree.leavesSubBlockIndex(); }

    protected SaplingBlock getSaplingBlock() { return tree.saplingBlock(); }

    protected void placeLeaves(World world, BlockPos blockpos)
    {
    	IBlockState state = world.getBlockState(blockpos);
    	if (canGrowInto(state.getBlock()))
            setBlockAndNotifyAdequately(world, blockpos, getLeavesBlock().getDefaultState());
    }

    protected void placeLog(World world, BlockPos blockpos)
    {
    	IBlockState state = world.getBlockState(blockpos);
    	if (canGrowInto(state.getBlock()))
    		setBlockAndNotifyAdequately(world, blockpos, getLogBlock().getDefaultState());
    }
}
