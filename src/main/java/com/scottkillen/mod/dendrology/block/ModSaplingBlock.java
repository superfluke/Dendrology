package com.scottkillen.mod.dendrology.block;

import com.google.common.collect.ImmutableList;
import com.scottkillen.mod.dendrology.TheMod;
import com.scottkillen.mod.dendrology.content.ProvidesPotionEffect;
import com.scottkillen.mod.kore.tree.DefinesSapling;
import com.scottkillen.mod.kore.tree.block.SaplingBlock;
import net.minecraft.item.ItemStack;

public final class ModSaplingBlock extends SaplingBlock
{
    public ModSaplingBlock(Iterable<? extends DefinesSapling> subBlocks)
    {
        super(ImmutableList.copyOf(subBlocks));
        setCreativeTab(TheMod.CREATIVE_TAB);
        setHardness(0.0F);
        setStepSound(soundTypeGrass);
    }

    @Override
    protected String resourcePrefix() { return TheMod.getResourcePrefix(); }

    @SuppressWarnings("ReturnOfNull")
    public String getPotionEffect(ItemStack itemStack)
    {
        final ImmutableList<DefinesSapling> subBlocks = subBlocks();
        final int itemDamage = itemStack.getItemDamage();
        if (itemDamage < 0 || itemDamage >= subBlocks.size()) return null;

        final DefinesSapling subBlock = subBlocks.get(itemDamage);
        return subBlock instanceof ProvidesPotionEffect ? ((ProvidesPotionEffect) subBlock).getPotionEffect() : null;
    }
}
