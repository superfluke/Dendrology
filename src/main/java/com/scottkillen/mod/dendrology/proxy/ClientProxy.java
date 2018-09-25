package com.scottkillen.mod.dendrology.proxy;

import com.scottkillen.mod.dendrology.world.AcemusColorizer;
import com.scottkillen.mod.dendrology.world.CerasuColorizer;
import com.scottkillen.mod.dendrology.world.KulistColorizer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;

public class ClientProxy extends CommonProxy
{
	@Override
    public void postInit()
    {
        final IResourceManager resourceManager = Minecraft.getMinecraft().getResourceManager();
        if (resourceManager instanceof IReloadableResourceManager)
        {
            // These casts are bad, but we don't have another way to do this.
            ((IReloadableResourceManager) resourceManager).registerReloadListener(AcemusColorizer.INSTANCE);
            ((IReloadableResourceManager) resourceManager).registerReloadListener(CerasuColorizer.INSTANCE);
            ((IReloadableResourceManager) resourceManager).registerReloadListener(KulistColorizer.INSTANCE);
        }
    }

}
