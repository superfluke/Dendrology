package com.scottkillen.mod.dendrology.item;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import com.scottkillen.mod.dendrology.TheMod;
import com.scottkillen.mod.dendrology.config.Settings;

@ObjectHolder(TheMod.MOD_ID)
public final class ModItems
{
    public static final Item parcel = new SaplingParcel();

    public void loadContent()//TODO
    {
//        GameRegistry.registerItem(parcel, "parcel");
//
//        addParcelToChests();
    }

    private static void addParcelToChests()
    {
        for (final String chestType : Settings.chestTypes())
            addParcelToChest(chestType);
    }

    private static void addParcelToChest(String chestType) //TODO loottables
    {
//        final int rarity = Settings.INSTANCE.chestRarity(chestType);
//        if (rarity <= 0) return;
//
//        final ItemStack parcelStack = new ItemStack(parcel);
//        final WeightedRandomChestContent chestContent = new WeightedRandomChestContent(parcelStack, 1, 2, rarity);
//
//        final ChestGenHooks chestGenInfo = ChestGenHooks.getInfo(chestType);
//        chestGenInfo.addItem(chestContent);
    }
}
