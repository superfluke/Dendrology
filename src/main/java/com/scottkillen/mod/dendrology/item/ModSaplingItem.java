package com.scottkillen.mod.dendrology.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import com.google.common.base.Objects;
import com.scottkillen.mod.dendrology.block.ModSaplingBlock;
import com.scottkillen.mod.dendrology.kore.tree.item.SaplingItem;

public final class ModSaplingItem extends SaplingItem
{
    private final ModSaplingBlock sapling;

    public ModSaplingItem(Block block, ModSaplingBlock sapling, String[] names)
    {
        super(block, sapling, names);
        this.sapling = sapling;
    }

    @Override
    public String getPotionEffect(ItemStack itemStack)
    {
        return sapling.getPotionEffect(itemStack);
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("sapling", sapling).toString();
    }
}
