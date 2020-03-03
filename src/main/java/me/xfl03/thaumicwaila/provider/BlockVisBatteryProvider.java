package me.xfl03.thaumicwaila.provider;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import me.xfl03.thaumicwaila.ThaumicWaila;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import thaumcraft.api.crafting.IStabilizable;
import thaumcraft.common.blocks.devices.BlockVisBattery;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class BlockVisBatteryProvider implements IWailaDataProvider {
    @Nonnull
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        Block block = accessor.getBlock();
        IBlockState state = accessor.getBlockState();
        int charge = block.getMetaFromState(state);
        int max = getMax();
        tooltip.add(String.format("%s / %s", charge, max));

        return tooltip;
    }

    private int max = 0;

    public int getMax() {
        if (max != 0) {
            return max;
        }
        max = BlockVisBattery.CHARGE.getAllowedValues().size() - 1;
        return max;
    }

    public static void register(IWailaRegistrar registrar) {
        try{
            Class.forName("thaumcraft.common.blocks.devices.BlockVisBattery");
        }catch(Exception ignored){
            ThaumicWaila.LOG.warn("CLASS 'thaumcraft.common.blocks.devices.BlockVisBattery' not detected.");
            return;
        }

        registrar.registerBodyProvider(new BlockVisBatteryProvider(), BlockVisBattery.class);
        ThaumicWaila.LOG.info("BlockVisBatteryProvider registered.");
    }
}
