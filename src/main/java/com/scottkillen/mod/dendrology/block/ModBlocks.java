package com.scottkillen.mod.dendrology.block;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.scottkillen.mod.dendrology.TheMod;
import com.scottkillen.mod.dendrology.content.overworld.OverworldTreeBlockFactory;
import com.scottkillen.mod.dendrology.content.overworld.OverworldTreeTaxonomy;
import com.scottkillen.mod.dendrology.kore.common.block.SlabBlock;
import com.scottkillen.mod.dendrology.kore.common.block.StairsBlock;
import com.scottkillen.mod.dendrology.kore.tree.DefinesLog;
import com.scottkillen.mod.dendrology.kore.tree.DefinesSapling;
import com.scottkillen.mod.dendrology.kore.tree.DefinesSlab;
import com.scottkillen.mod.dendrology.kore.tree.DefinesStairs;
import com.scottkillen.mod.dendrology.kore.tree.block.LeavesBlock;
import com.scottkillen.mod.dendrology.kore.tree.block.LogBlock;
import com.scottkillen.mod.dendrology.kore.tree.block.SaplingBlock;
import com.scottkillen.mod.dendrology.kore.tree.block.WoodBlock;
import com.scottkillen.mod.dendrology.kore.tree.loader.TreeSpeciesLoader;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;


public final class ModBlocks
{
    private static final int DEFAULT_LEAVES_FIRE_ENCOURAGEMENT = 30;
    private static final int DEFAULT_LOG_FIRE_ENCOURAGEMENT = 5;
    private static final int DEFAULT_PLANKS_FIRE_ENCOURAGEMENT = 5;
    private static final int DEFAULT_STAIRS_FIRE_ENCOURAGEMENT = DEFAULT_PLANKS_FIRE_ENCOURAGEMENT;
    private static final int DEFAULT_LEAVES_FLAMMABILITY = 60;
    private static final int DEFAULT_LOG_FLAMMABILITY = 5;
    private static final int DEFAULT_PLANKS_FLAMMABILITY = 20;
    private static final int DEFAULT_STAIRS_FLAMMABILITY = DEFAULT_PLANKS_FLAMMABILITY;
    private static final List<LogBlock> logBlocks = Lists.newArrayList();
    private static final List<WoodBlock> woodBlocks = Lists.newArrayList();
    private static final List<SlabBlock> singleSlabBlocks = Lists.newArrayList();
    private static final List<SlabBlock> doubleSlabBlocks = Lists.newArrayList();
    private static final List<StairsBlock> stairsBlocks = Lists.newArrayList();
    private static final List<SaplingBlock> saplingBlocks = Lists.newArrayList();
    private static final List<LeavesBlock> leavesBlocks = Lists.newArrayList();
    private static final OverworldTreeTaxonomy overworldTaxonomy = new OverworldTreeTaxonomy();

    private static void addAllSaplingsToChests()
    {
//        TheMod.logger.info("Hiding saplings in chests.");
//        final Settings settings = Settings.INSTANCE;
//
//        for (final DefinesSapling saplingDefinition : overworldTaxonomy.saplingDefinitions())
//            for (final String chestType : Settings.chestTypes())
//                addSaplingToChest(saplingDefinition, chestType, settings.chestRarity(chestType));
    }

    private static void addSaplingToChest(DefinesSapling saplingDefinition, String chestType, int rarity)
    {//TODO
//        if (rarity <= 0) return;
//
//        final ItemStack sapling =
//                new ItemStack(saplingDefinition.saplingBlock(), 1, saplingDefinition.saplingSubBlockIndex());
//        final WeightedRandomChestContent chestContent = new WeightedRandomChestContent(sapling, 1, 2, rarity);
//
//        final ChestGenHooks chestGenInfo = ChestGenHooks.getInfo(chestType);
//        chestGenInfo.addItem(chestContent);
    }

    public static Iterable<? extends BlockLeaves> leavesBlocks() { return ImmutableList.copyOf(leavesBlocks); }

    private static void loadOverWorldContent()
    {
        TheMod.logger.info("Loading overworld species.");
        final TreeSpeciesLoader overworldContent = new TreeSpeciesLoader(overworldTaxonomy);
        overworldContent.load(new OverworldTreeBlockFactory());
    }

    public static Iterable<? extends BlockLog> logBlocks() { return ImmutableList.copyOf(logBlocks); }

    public static Iterable<? extends DefinesLog> logDefinitions() { return overworldTaxonomy.logDefinitions(); }

    @SubscribeEvent
    private static void registerBlocks(RegistryEvent.Register<Block> event)
    {
    	IForgeRegistry<Block> forgeRegistry = event.getRegistry();
        registerAllLogBlocks(forgeRegistry);
        registerAllLeavesBlock(forgeRegistry);
        registerAllSaplingBlocks(forgeRegistry);
        registerAllWoodBlocks(forgeRegistry);
        registerAllStairsBlocks(forgeRegistry);
        registerAllSingleSlabBlocks(forgeRegistry);
        registerAllDoubleSlabBlocks(forgeRegistry);
    }

    private static void registerAllDoubleSlabBlocks(IForgeRegistry<Block> forgeRegistry)
    {
        int slabCount = 0;
        for (final SlabBlock slab : doubleSlabBlocks)
        {
//            registerSlabBlock(slab, String.format("dslab%d", slabCount), singleSlabBlocks.get(slabCount), slab, true);
        	forgeRegistry.register(slab);
            slabCount++;
        }
    }

    private static void registerAllLeavesBlock(IForgeRegistry<Block> forgeRegistry)
    {
        int leavesCount = 0;
        for (final Block block : leavesBlocks)
        {
            //registerLeavesBlock(block, String.format("leaves%d", leavesCount));
        	forgeRegistry.register(block);
        	Blocks.FIRE.setFireInfo(block, DEFAULT_LEAVES_FIRE_ENCOURAGEMENT, DEFAULT_LEAVES_FLAMMABILITY);
            leavesCount++;
        }
    }

    private static void registerAllLogBlocks(IForgeRegistry<Block> forgeRegistry)
    {
        int logCount = 0;
        for (final LogBlock block : logBlocks)
        {
            //registerLogBlock(block, String.format("logs%d", logCount), block.getSubBlockNames());
            forgeRegistry.register(block);
            Blocks.FIRE.setFireInfo(block, DEFAULT_LOG_FIRE_ENCOURAGEMENT, DEFAULT_LOG_FLAMMABILITY);
            logCount++;
        }
    }

    private static void registerAllSaplingBlocks(IForgeRegistry<Block> forgeRegistry)
    {
        int saplingCount = 0;

        for (final SaplingBlock sapling : saplingBlocks)
        {
//            registerSaplingBlock(sapling, String.format("sapling%d", saplingCount), sapling.subBlockNames());
        	forgeRegistry.register(sapling);
            saplingCount++;
        }
    }

    private static void registerAllSingleSlabBlocks(IForgeRegistry<Block> forgeRegistry)
    {
        int slabCount = 0;
        for (final SlabBlock slab : singleSlabBlocks)
        {
//            registerSlabBlock(slab, String.format("sslab%d", slabCount), slab, doubleSlabBlocks.get(slabCount), false);
        	forgeRegistry.register(slab);
            slabCount++;
        }
    }

    private static void registerAllStairsBlocks(IForgeRegistry<Block> forgeRegistry)
    {
        int stairsCount = 0;
        for (final StairsBlock stairs : stairsBlocks)
        {
            //registerStairsBlock(stairs, String.format("stairs%d", stairsCount));
            forgeRegistry.register(stairs);
            Blocks.FIRE.setFireInfo(stairs, DEFAULT_STAIRS_FIRE_ENCOURAGEMENT, DEFAULT_STAIRS_FLAMMABILITY);
            stairsCount++;
        }
    }

    private static void registerAllWoodBlocks(IForgeRegistry<Block> forgeRegistry)
    {
        int woodBlockCount = 0;
        for (final WoodBlock wood : woodBlocks)
        {
            //registerWoodBlock(wood, String.format("wood%d", woodBlockCount), wood.getSubBlockNames());
            forgeRegistry.register(wood);
            Blocks.FIRE.setFireInfo(wood, DEFAULT_PLANKS_FIRE_ENCOURAGEMENT, DEFAULT_PLANKS_FLAMMABILITY);
            woodBlockCount++;
        }
    }

    public static void registerBlock(LeavesBlock leavesBlock) { leavesBlocks.add(leavesBlock); }

    public static void registerBlock(LogBlock logBlock) { logBlocks.add(logBlock); }

    public static void registerBlock(SaplingBlock saplingBlock) { saplingBlocks.add(saplingBlock); }

    public static void registerBlock(SlabBlock singleSlabBlock, SlabBlock doubleSlabBlock)
    {
        singleSlabBlocks.add(singleSlabBlock);
        doubleSlabBlocks.add(doubleSlabBlock);
    }

    public static void registerBlock(StairsBlock stairsBlock) { stairsBlocks.add(stairsBlock); }

    public static void registerBlock(WoodBlock woodBlock) { woodBlocks.add(woodBlock); }

//    private static void registerLeavesBlock(Block block, String name)
//    {
//        GameRegistry.registerBlock(block, LeavesItem.class, name);
//        Blocks.FIRE.setFireInfo(block, DEFAULT_LEAVES_FIRE_ENCOURAGEMENT, DEFAULT_LEAVES_FLAMMABILITY);
//    }

//    private static void registerLogBlock(Block block, String name, ImmutableList<String> subblockNames)
//    {
//        GameRegistry.registerBlock(block, ModLogItem.class, name, block,
//                subblockNames.toArray(new String[subblockNames.size()]));
//        Blocks.FIRE.setFireInfo(block, DEFAULT_LOG_FIRE_ENCOURAGEMENT, DEFAULT_LOG_FLAMMABILITY);
//    }

//    private static void registerSaplingBlock(Block block, String name, List<String> subblockNames)
//    {
//        GameRegistry.registerBlock(block, ModSaplingItem.class, name, block,
//                subblockNames.toArray(new String[subblockNames.size()]));
//    }

//    private static void registerSlabBlock(Block block, String name, SlabBlock singleSlab, SlabBlock doubleSlab,
//                                          boolean isDouble)
//    {
//        GameRegistry.registerBlock(block, ModSlabItem.class, name, singleSlab, doubleSlab, isDouble);
//    }

//    private static void registerStairsBlock(Block block, String name)
//    {
//        GameRegistry.registerBlock(block, name);
//        Blocks.FIRE.setFireInfo(block, DEFAULT_STAIRS_FIRE_ENCOURAGEMENT, DEFAULT_STAIRS_FLAMMABILITY);
//    }

//    private static void registerWoodBlock(Block block, String name, ImmutableList<String> subblockNames)
//    {
//        GameRegistry.registerBlock(block, ModWoodItem.class, name, block,
//                subblockNames.toArray(new String[subblockNames.size()]));
//        Blocks.FIRE.setFireInfo(block, DEFAULT_PLANKS_FIRE_ENCOURAGEMENT, DEFAULT_PLANKS_FLAMMABILITY);
//    }

    public static Iterable<? extends BlockSapling> saplingBlocks() { return ImmutableList.copyOf(saplingBlocks); }

    public static Iterable<? extends Block> singleSlabBlocks() { return ImmutableList.copyOf(singleSlabBlocks); }

    public static Iterable<? extends DefinesSlab> slabDefinitions() { return overworldTaxonomy.slabDefinitions(); }

    public static Iterable<? extends Block> stairsBlocks() { return ImmutableList.copyOf(stairsBlocks); }

    public static Iterable<? extends DefinesStairs> stairsDefinitions()
    {
        return overworldTaxonomy.stairsDefinitions();
    }

    public static Iterable<? extends Block> woodBlocks() { return ImmutableList.copyOf(woodBlocks); }

    public void loadContent()
    {
        loadOverWorldContent();
//        registerAllBlocks();
        addAllSaplingsToChests();
    }
}
