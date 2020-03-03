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

import javax.annotation.Nonnull;
import java.util.List;

public class StabilizableProvider implements IWailaDataProvider {
    @Nonnull
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        TileEntity te = accessor.getTileEntity();
        if (!(te instanceof IStabilizable)) {
            return tooltip;
        }

        IStabilizable stabilizable = (IStabilizable) te;
        tooltip.add(I18n.format("stability." + stabilizable.getStability().name()));

        return tooltip;
    }

    public static void register(IWailaRegistrar registrar) {
        try{
            Class.forName("thaumcraft.api.crafting.IStabilizable");
        }catch(Exception ignored){
            ThaumicWaila.LOG.warn("CLASS 'thaumcraft.api.crafting.IStabilizable' not detected.");
            return;
        }

        registrar.registerBodyProvider(new StabilizableProvider(), IStabilizable.class);
        ThaumicWaila.LOG.info("StabilizableProvider registered.");
    }
}
