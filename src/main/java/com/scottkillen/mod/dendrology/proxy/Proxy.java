package com.scottkillen.mod.dendrology.proxy;

import net.minecraftforge.fml.common.SidedProxy;

import com.scottkillen.mod.dendrology.proxy.render.RenderProxy;

public enum Proxy
{
    ;
    private static final String CLIENT_RENDER_PROXY_CLASS =
            "com.scottkillen.mod.dendrology.proxy.render.ClientRenderProxy";
    private static final String RENDER_PROXY_CLASS = "com.scottkillen.mod.dendrology.proxy.render.RenderProxy";

    @SidedProxy(clientSide = CLIENT_RENDER_PROXY_CLASS, serverSide = RENDER_PROXY_CLASS)
    public static RenderProxy render;
}
