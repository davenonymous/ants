package org.dave.ants.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import org.dave.ants.Ants;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.chambers.IChamberRegistry;
import org.dave.ants.hills.HillItemStackData;
import org.dave.ants.init.Blockss;
import org.dave.ants.init.Itemss;
import org.dave.ants.render.BakedModelLoader;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        Blockss.initModels();
        // Register ant chamber item models
        Item itemBlock = Item.REGISTRY.getObject(new ResourceLocation(Ants.MODID, "hill_chamber"));

        for(Class<? extends IAntChamber> antChamberType : Ants.chamberTypes.getChamberTypes()) {
            ResourceLocation newResLoc = new ResourceLocation(Ants.MODID, "hill_chamber_" + antChamberType.getSimpleName().toLowerCase());
            ModelLoader.registerItemVariants(itemBlock, newResLoc);
        }

        Itemss.initModels();
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);


        ModelLoaderRegistry.registerLoader(new BakedModelLoader());
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        Item itemBlock = Item.REGISTRY.getObject(new ResourceLocation(Ants.MODID, "hill_chamber"));
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> {
            HillItemStackData data = new HillItemStackData(stack);
            if(data == null) {
                return -1;
            }

            IAntChamber chamber = Ants.chamberTypes.getChamberInstance(data);
            if(chamber == null) {
                return -1;
            }

            if(!chamber.useColoredAntModel()) {
                return -1;
            }

            if(tintIndex == 0) {
                // layer 0, the border
                return chamber.antStrokeColorTint();
            }

            if(tintIndex == 1) {
                // layer 1, the filling
                return chamber.antFillingColorTint();
            }

            if(tintIndex == 2) {
                // layer 2, the wings
                return chamber.antWingsColorTint();
            }

            return -1;
        }, itemBlock);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

        Blockss.initItemModels();
    }
}
