package com.scottkillen.mod.dendrology;

import org.apache.logging.log4j.Logger;

import com.scottkillen.mod.dendrology.block.ModBlocks;
import com.scottkillen.mod.dendrology.content.ParcelManager;
import com.scottkillen.mod.dendrology.content.crafting.Crafter;
import com.scottkillen.mod.dendrology.content.crafting.OreDictHandler;
import com.scottkillen.mod.dendrology.content.crafting.Smelter;
import com.scottkillen.mod.dendrology.content.fuel.FuelHandler;
import com.scottkillen.mod.dendrology.content.overworld.OverworldTreeGenerator;
import com.scottkillen.mod.dendrology.content.overworld.OverworldTreeSpecies;
import com.scottkillen.mod.dendrology.item.ModItems;
import com.scottkillen.mod.dendrology.proxy.CommonProxy;
import com.scottkillen.mod.dendrology.proxy.Proxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = TheMod.MOD_ID, name = TheMod.MOD_NAME, version = TheMod.MOD_VERSION)
public final class TheMod
{
    public static final String MOD_ID = "dendrology";
    static final String MOD_NAME = "Ancient Trees";
    static final String MOD_VERSION = "${mod_version}";
    static final String MOD_GUI_FACTORY = "com.scottkillen.mod.dendrology.config.client.ModGuiFactory";
    private static final String RESOURCE_PREFIX = MOD_ID.toLowerCase() + ':';

    @Instance(MOD_ID)
    public static TheMod INSTANCE;
    @SidedProxy(clientSide = "com.scottkillen.mod.dendrology.proxy.ClientProxy", serverSide = "com.scottkillen.mod.dendrology.proxy.CommonProxy")
	public static CommonProxy proxy;
    
    private final CreativeTabs creativeTab = new CreativeTabs(MOD_ID.toLowerCase())
    {
        private final OverworldTreeSpecies ICON = OverworldTreeSpecies.PORFFOR;

        @SideOnly(Side.CLIENT)
        @Override
        public ItemStack getIconItemStack()
        {
            return new ItemStack(ICON.saplingBlock(), 1, ICON.saplingSubBlockIndex());
        }

        @SideOnly(Side.CLIENT)
        @Override
        public ItemStack getTabIconItem() { return null; }
    };

    public static String getResourcePrefix() { return RESOURCE_PREFIX; }

    public static Logger logger;

    public CreativeTabs creativeTab()
    {
        return creativeTab;
    }

    @EventHandler
    public void onFMLPreInitialization(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
//        configEventHandler = Optional.of(
//                new ConfigEventHandler(MOD_ID, event.getSuggestedConfigurationFile(), Settings.INSTANCE,
//                        Settings.CONFIG_VERSION));
//        configEventHandler.get().activate();
    	MinecraftForge.EVENT_BUS.register(new ModBlocks());
        new ModBlocks().loadContent();
        new ModItems().loadContent();
    }

    @EventHandler
    public void onFMLInitialization(FMLInitializationEvent event)
    {
        logger.info("Adding recipes.");
        new OreDictHandler().registerBlocksWithOreDictinary();
        new Crafter().writeRecipes();
        new Smelter().registerSmeltings();
    }

    @EventHandler
    public void onFMLPostInitialization(FMLPostInitializationEvent event)
    {
        proxy.postInit();
        FuelHandler.postInit();
        ParcelManager.INSTANCE.init();

        new OverworldTreeGenerator().install();
    }
}
