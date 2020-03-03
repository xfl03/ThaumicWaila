package me.xfl03.thaumicwaila.provider;

import com.mojang.realmsclient.gui.ChatFormatting;
import mcp.mobius.waila.api.*;
import me.xfl03.thaumicwaila.ThaumicWaila;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.apache.commons.lang3.text.WordUtils;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

import static mcp.mobius.waila.api.SpecialChars.TAB;
import static mcp.mobius.waila.api.SpecialChars.ALIGNRIGHT;

public class AspectContainerProvider implements IWailaDataProvider {
    @Nonnull
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        //NBTTagCompound tag = accessor.getNBTData();
        //ThaumicWaila.LOG.info("getWailaBody: " + tag.toString());

        TileEntity te = accessor.getTileEntity();
        if (!(te instanceof IAspectContainer)) {
            return tooltip;
        }

        IAspectContainer container = (IAspectContainer) te;
        AspectList list = container.getAspects();
        if (list == null || list.size() <= 0) {
            return tooltip;
        }

        Map<Aspect, Integer> aspects = list.aspects;

        //Add tooltip
        for (Aspect aspect : aspects.keySet()) {
            processAspect(tooltip, aspect.getTag(), aspects.get(aspect));
        }

        return tooltip;
    }

    private static void processAspect(List<String> tooltip, String aspect, int amount) {
        String str =
                SpecialChars.getRenderString("thaumicwaila.aspect", aspect) +
                        String.format("%s" + TAB + ALIGNRIGHT + ChatFormatting.WHITE + "%d  ",
                                WordUtils.capitalizeFully(aspect), amount);
        tooltip.add(str);
    }

    public static void register(IWailaRegistrar registrar) {
        try{
            Class.forName("thaumcraft.api.aspects.IAspectContainer");
        }catch(Exception ignored){
            ThaumicWaila.LOG.warn("CLASS 'thaumcraft.api.aspects.IAspectContainer' not detected.");
            return;
        }

        AspectContainerProvider provider = new AspectContainerProvider();
        registrar.registerBodyProvider(provider, IAspectContainer.class);
        //registar.registerNBTProvider(provider, IAspectContainer.class);

        ThaumicWaila.LOG.info("AspectContainerProvider registered.");
    }
}
