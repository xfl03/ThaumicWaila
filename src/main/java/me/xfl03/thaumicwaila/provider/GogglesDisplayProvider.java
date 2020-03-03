package me.xfl03.thaumicwaila.provider;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import me.xfl03.thaumicwaila.ThaumicWaila;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.crafting.IStabilizable;
import thaumcraft.api.items.IGogglesDisplayExtended;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class GogglesDisplayProvider implements IWailaDataProvider {
    @Nonnull
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        TileEntity te = accessor.getTileEntity();
        if (!(te instanceof IGogglesDisplayExtended)) {
            return tooltip;
        }

        IGogglesDisplayExtended display = (IGogglesDisplayExtended) te;
        tooltip.addAll(Arrays.asList(display.getIGogglesText()));

        return tooltip;
    }

    public static void register(IWailaRegistrar registrar) {
        try {
            Class.forName("thaumcraft.api.items.IGogglesDisplayExtended");
        } catch (Exception ignored) {
            ThaumicWaila.LOG.warn("CLASS 'thaumcraft.api.items.IGogglesDisplayExtended' not detected.");
            return;
        }

        registrar.registerBodyProvider(new GogglesDisplayProvider(), IGogglesDisplayExtended.class);
        ThaumicWaila.LOG.info("GogglesDisplayProvider registered.");
    }
}
