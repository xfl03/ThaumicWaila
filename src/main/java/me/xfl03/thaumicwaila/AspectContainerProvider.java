package me.xfl03.thaumicwaila;

import com.mojang.realmsclient.gui.ChatFormatting;
import mcp.mobius.waila.api.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.text.WordUtils;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.List;

import static mcp.mobius.waila.api.SpecialChars.TAB;

public class AspectContainerProvider implements IWailaDataProvider {
    @Nonnull
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        NBTTagCompound tag = accessor.getNBTData();
        //ThaumicWaila.LOG.info("getWailaBody: " + tag.toString());

        if (tag.hasKey("aspect") && tag.hasKey("amount")) {
            processAspect(tooltip, tag.getString("aspect"), tag.getShort("amount"));
        }

        if (tag.hasKey("Aspect") && tag.hasKey("Amount")) {
            processAspect(tooltip, tag.getString("Aspect"), tag.getShort("Amount"));
        }


        if (tag.hasKey("Aspects")) {
            AspectList list = new AspectList();
            list.readFromNBT(tag);
            LinkedHashMap<Aspect, Integer> map = list.aspects;
            for (Aspect aspect : map.keySet()) {
                processAspect(tooltip, aspect.getTag(), map.get(aspect));
            }
        }
        return tooltip;
    }

    @Nonnull
    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        //I don't know why this method wouldn't be called.
        ThaumicWaila.LOG.info("getNBTData: " + tag.toString());

        IAspectContainer container = (IAspectContainer) te;
        AspectList aspectList = container.getAspects();
        aspectList.writeToNBT(tag);

        return tag;
    }

    private void processAspect(List<String> tooltip, String aspect, int amount) {

        String str =
                SpecialChars.getRenderString("thaumicwaila.aspect", aspect) +
                String.format("%s" + TAB + ChatFormatting.WHITE + "%d  ", WordUtils.capitalizeFully(aspect), amount);
        tooltip.add(str);
    }

    public static void register(IWailaRegistrar registar) {
        AspectContainerProvider provider = new AspectContainerProvider();
        registar.registerBodyProvider(provider, IAspectContainer.class);
        registar.registerNBTProvider(provider, IAspectContainer.class);

        ThaumicWaila.LOG.info("AspectContainerProvider registered.");
    }
}
