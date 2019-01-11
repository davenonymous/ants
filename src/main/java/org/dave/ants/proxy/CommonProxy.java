package org.dave.ants.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import org.dave.ants.Ants;
import org.dave.ants.base.BaseItemBlock;
import org.dave.ants.blocks.ChamberHillBlock;
import org.dave.ants.blocks.ChamberHillItemBlock;
import org.dave.ants.blocks.EntranceHillBlock;
import org.dave.ants.init.Blockss;
import org.dave.ants.network.AntsNetworkHandler;
import org.dave.ants.tiles.ChamberHillTile;
import org.dave.ants.tiles.EntranceHillTile;

@Mod.EventBusSubscriber
public class CommonProxy {
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> reg = event.getRegistry();
        reg.register(new EntranceHillBlock());
        reg.register(new ChamberHillBlock());

        GameRegistry.registerTileEntity(EntranceHillTile.class, new ResourceLocation(Ants.MODID, "hill_entrance"));
        GameRegistry.registerTileEntity(ChamberHillTile.class, new ResourceLocation(Ants.MODID, "hill_chamber"));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> reg = event.getRegistry();
        reg.register(new BaseItemBlock(Blockss.hillEntrance, true));
        reg.register(new ChamberHillItemBlock(Blockss.chamber, true));
    }

    public void preInit(FMLPreInitializationEvent event) {
    }

    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Ants.instance, new GuiProxy());
    }

    public void postInit(FMLPostInitializationEvent event) {
        AntsNetworkHandler.init();
    }
}
