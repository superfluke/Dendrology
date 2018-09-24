package com.scottkillen.mod.dendrology.block;

import java.util.List;

import net.minecraft.item.ItemStack;

import com.google.common.collect.ImmutableList;
import com.scottkillen.mod.dendrology.TheMod;
import com.scottkillen.mod.dendrology.content.ProvidesPotionEffect;
import com.scottkillen.mod.dendrology.kore.tree.DefinesSapling;
import com.scottkillen.mod.dendrology.kore.tree.block.SaplingBlock;

public final class ModSaplingBlock extends SaplingBlock
{
    public ModSaplingBlock(Iterable<? extends DefinesSapling> subBlocks)
    {
        super(ImmutableList.copyOf(subBlocks));
        setCreativeTab(TheMod.INSTANCE.creativeTab());
        setHardness(0.0F);
        setStepSound(soundTypeGrass);
    }

    @Override
    protected String resourcePrefix() { return TheMod.getResourcePrefix(); }

    public String getPotionEffect(ItemStack itemStack)
    {
        final List<DefinesSapling> subBlocks = subBlocks();
        final int itemDamage = itemStack.getItemDamage();
        if (itemDamage < 0 || itemDamage >= subBlocks.size()) return null;

        final DefinesSapling subBlock = subBlocks.get(itemDamage);
        return subBlock instanceof ProvidesPotionEffect ? ((ProvidesPotionEffect) subBlock).potionEffect() : null;
    }
}
