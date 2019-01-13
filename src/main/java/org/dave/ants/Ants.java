package org.dave.ants;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.dave.ants.actions.ActionRegistry;
import org.dave.ants.calculation.CalculationRegistry;
import org.dave.ants.chambers.ChamberRegistry;
import org.dave.ants.compat.AntApiHelpers;
import org.dave.ants.compat.CompatHandler;
import org.dave.ants.compat.PluginRegistry;
import org.dave.ants.config.ConfigurationHandler;
import org.dave.ants.gui.ClientHillData;
import org.dave.ants.hills.HillPropertyRegistry;
import org.dave.ants.misc.CreativeTabAnts;
import org.dave.ants.misc.WorldSavedDataRegistry;
import org.dave.ants.proxy.CommonProxy;
import org.dave.ants.chambers.VoxelHandlerRegistry;
import org.dave.ants.util.AnnotatedInstanceUtil;
import org.dave.ants.util.Logz;

@Mod(
        modid = Ants.MODID,
        version = Ants.VERSION,
        name = "Ants",
        guiFactory = Ants.GUI_FACTORY,
        acceptedMinecraftVersions = "[1.12,1.13)"
)
public class Ants {
    public static final String MODID = "ants";
    public static final String VERSION = "1.0.0";
    public static final String GUI_FACTORY = "org.dave.ants.misc.ConfigGuiFactory";

    @Mod.Instance(Ants.MODID)
    public static Ants instance;

    @SidedProxy(clientSide = "org.dave.ants.proxy.ClientProxy", serverSide = "org.dave.ants.proxy.ServerProxy")
    public static CommonProxy proxy;

    public static final CreativeTabAnts CREATIVE_TAB = new CreativeTabAnts();

    public static final WorldSavedDataRegistry savedData = new WorldSavedDataRegistry();
    public static final ClientHillData clientHillData = new ClientHillData();

    public static final PluginRegistry pluginRegistry = new PluginRegistry();
    public static final HillPropertyRegistry hillProperties = new HillPropertyRegistry();
    public static final ChamberRegistry chamberTypes = new ChamberRegistry();
    public static final CalculationRegistry calculations = new CalculationRegistry();
    public static final VoxelHandlerRegistry voxelHandlers = new VoxelHandlerRegistry();
    public static final ActionRegistry actionRegistry = new ActionRegistry();
    public static final AntApiHelpers apiHelpers = new AntApiHelpers();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Logz.logger = event.getModLog();

        AnnotatedInstanceUtil.asmDataTable = event.getAsmData();

        // First load all plugins
        pluginRegistry.loadAntsPlugins();

        // Then initialize all registries and notify each plugin accordingly
        hillProperties.findHillProperties();
        pluginRegistry.forEach(plugin -> plugin.registryReady(hillProperties));
        chamberTypes.findChamberIntegrations();
        pluginRegistry.forEach(plugin -> plugin.registryReady(chamberTypes));
        calculations.loadAntCalculations();
        pluginRegistry.forEach(plugin -> plugin.registryReady(calculations));
        voxelHandlers.loadVoxelHandlers();
        pluginRegistry.forEach(plugin -> plugin.registryReady(voxelHandlers));
        pluginRegistry.forEach(plugin -> plugin.registryReady(actionRegistry));
        pluginRegistry.forEach(plugin -> plugin.helpersReady(apiHelpers));

        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(ConfigurationHandler.class);
        MinecraftForge.EVENT_BUS.register(savedData);

        CompatHandler.registerCompat();

        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
