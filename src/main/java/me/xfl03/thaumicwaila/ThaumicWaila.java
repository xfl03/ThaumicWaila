package me.xfl03.thaumicwaila;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = "thaumicwaila",
        name = "ThaumicWaila",
        version = "@VERSION@",
        acceptableRemoteVersions = "*",
        dependencies = "required:forge@[14.23.5.2768,);required:waila@[1.8.20,);required:thaumcraft@[1.12.2-6.1,);"
)
public class ThaumicWaila {
    public static final Logger LOG = LogManager.getLogger("ThaumicWaila");
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        FMLInterModComms.sendMessage("waila", "register", "me.xfl03.thaumicwaila.provider.AspectContainerProvider.register");
        FMLInterModComms.sendMessage("waila", "register", "me.xfl03.thaumicwaila.provider.BlockVisBatteryProvider.register");
        FMLInterModComms.sendMessage("waila", "register", "me.xfl03.thaumicwaila.provider.GogglesDisplayProvider.register");
        FMLInterModComms.sendMessage("waila", "register", "me.xfl03.thaumicwaila.provider.StabilizableProvider.register");
        FMLInterModComms.sendMessage("waila", "register", "me.xfl03.thaumicwaila.render.AspectRender.register");
    }
}
