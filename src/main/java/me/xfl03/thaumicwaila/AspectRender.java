package me.xfl03.thaumicwaila;

import mcp.mobius.waila.api.IWailaCommonAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.IWailaTooltipRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import thaumcraft.api.aspects.Aspect;

import javax.annotation.Nonnull;
import java.awt.*;

public class AspectRender implements IWailaTooltipRenderer {
    @Nonnull
    @Override
    public Dimension getSize(String[] args, IWailaCommonAccessor accessor) {
        return new Dimension(8, 8);
    }

    @Override
    public void draw(String[] args, IWailaCommonAccessor accessor) {
        Aspect aspect = Aspect.getAspect(args[0]);
        //ThaumicWaila.LOG.info("Render aspect: " + aspect.getName());
        //ThaumicWaila.LOG.info("Render texture: " + aspect.getImage());
        //ThaumicWaila.LOG.info("Render texture: " + Minecraft.getMinecraft().getTextureManager().getTexture(aspect.getImage()));

        drawAspect(aspect);
    }

    private void drawAspect(Aspect aspect) {
        GlStateManager.pushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(aspect.getImage());
        GlStateManager.enableBlend();
        Color color = new Color(aspect.getColor());
        GlStateManager.color(
                (float) color.getRed() / 255.0F,
                (float) color.getGreen() / 255.0F,
                (float) color.getBlue() / 255.0F
        );
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 8, 8, 8, 8);
        GlStateManager.popMatrix();
    }

    public static void register(IWailaRegistrar registar) {
        registar.registerTooltipRenderer("thaumicwaila.aspect", new AspectRender());

        ThaumicWaila.LOG.info("AspectRender registered.");
    }
}
