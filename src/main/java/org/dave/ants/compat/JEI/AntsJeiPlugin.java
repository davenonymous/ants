package org.dave.ants.compat.JEI;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.dave.ants.Ants;
import org.dave.ants.hills.HillItemStackData;
import org.dave.ants.init.Blockss;

@JEIPlugin
public class AntsJeiPlugin implements IModPlugin {
    private static IJeiHelpers helpers;

    @Override
    public void register(IModRegistry registry) {
        helpers = registry.getJeiHelpers();

        IIngredientBlacklist ingredientBlacklist = helpers.getIngredientBlacklist();
        ingredientBlacklist.addIngredientToBlacklist(new ItemStack(Blockss.chamber));
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        Item itemBlock = Item.REGISTRY.getObject(new ResourceLocation(Ants.MODID, "hill_chamber"));
        subtypeRegistry.registerSubtypeInterpreter(itemBlock, itemStack -> {
            if(!itemStack.hasTagCompound()) {
                return String.format("%s:hill_chamber:null", Ants.MODID);
            }

            HillItemStackData stackData = new HillItemStackData(itemStack);
            if(!stackData.hasChamberType()) {
                return String.format("%s:hill_chamber:null", Ants.MODID);
            }

            return String.format("%s:hill_chamber:%s", Ants.MODID, stackData.getChamberType().getName());
        });
    }
}
