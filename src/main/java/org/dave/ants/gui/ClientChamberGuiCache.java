package org.dave.ants.gui;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import org.dave.ants.actions.ActionRegistry;
import org.dave.ants.actions.BuyChamber;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.gui.event.MouseClickEvent;
import org.dave.ants.api.gui.event.WidgetEventResult;
import org.dave.ants.api.gui.widgets.WidgetPanel;
import org.dave.ants.api.gui.widgets.WidgetTable;
import org.dave.ants.api.gui.widgets.composed.WidgetBuyChamberButton;
import org.dave.ants.api.gui.widgets.composed.WidgetLabeledProgressBar;
import org.dave.ants.api.hill.IHillProperty;
import org.dave.ants.api.properties.calculated.*;
import org.dave.ants.api.properties.stored.StoredFood;
import org.dave.ants.api.properties.stored.TotalAnts;
import org.dave.ants.chambers.ChamberRegistry;
import org.dave.ants.chambers.entrance.EntranceChamber;
import org.dave.ants.util.DimPos;
import org.dave.ants.util.SmartNumberFormatter;

import java.util.HashMap;
import java.util.Map;

public class ClientChamberGuiCache {
    public static Class<? extends IAntChamber> chamberType;
    public static NBTTagCompound chamberData;
    public static DimPos chamberPos;
    public static Map<Class<? extends IHillProperty>, IHillProperty> properties = new HashMap<>();
    public static Map<Class<? extends IAntChamber>, Integer> maxTierLevel = new HashMap<>();
    public static long lastMessageReceived;

    public static boolean hasData() {
        return chamberType != null && chamberData != null && chamberPos != null;
    }

    public static void reset() {
        chamberType = null;
        chamberData = null;
        chamberPos = null;
        properties.clear();
        maxTierLevel.clear();
    }

    public static IAntChamber getChamberInstance() {
        IAntChamber chamber = null;
        try {
            chamber = chamberType.newInstance();
            chamber.deserializeNBT(chamberData);
            return chamber;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }



    public static WidgetPanel createHillInfoPanel() {
        WidgetPanel hillInfos = new WidgetPanel();

        WidgetLabeledProgressBar totalAnts = new WidgetLabeledProgressBar(I18n.format("gui.ants.hill_chamber.tabs.stats.total_ants"), 0, getPropertyValue(MaxAnts.class), getPropertyValue(TotalAnts.class));
        totalAnts.setWidth(165);
        totalAnts.setHeight(20);
        totalAnts.setY(0);
        totalAnts.addTooltipLine(I18n.format("gui.ants.hill_chamber.stats.total_ants.tooltip.antsperhatching", SmartNumberFormatter.formatNumber(getPropertyValue(AntsBornPerHatching.class))));
        totalAnts.addTooltipLine(I18n.format("gui.ants.hill_chamber.stats.total_ants.tooltip.interval", SmartNumberFormatter.formatNumber((double)getPropertyValue(TicksBetweenBabies.class) / 20.0)));
        hillInfos.add(totalAnts);

        WidgetLabeledProgressBar storedFood = new WidgetLabeledProgressBar(I18n.format("gui.ants.hill_chamber.tabs.stats.stored_food"), 0, getPropertyValue(FoodCapacity.class), getPropertyValue(StoredFood.class));
        storedFood.setWidth(165);
        storedFood.setHeight(20);
        storedFood.setY(25);

        double eaten = getPropertyValue(FoodRequirementPerAnt.class) * getPropertyValue(TotalAnts.class) * 20;
        double gathered = getPropertyValue(FoodGainPerTick.class) * 20;
        double total =  gathered - eaten;

        storedFood.addTooltipLine(I18n.format("gui.ants.hill_chamber.stats.stored_food.tooltip.gathered", SmartNumberFormatter.formatNumber(gathered)));
        storedFood.addTooltipLine(I18n.format("gui.ants.hill_chamber.stats.stored_food.tooltip.eaten", SmartNumberFormatter.formatNumber(eaten)));
        storedFood.addTooltipLine(I18n.format("gui.ants.hill_chamber.stats.stored_food.tooltip.total", SmartNumberFormatter.formatNumber(total)));

        hillInfos.add(storedFood);

        // TODO: Add ability to buy ants for food

        return hillInfos;
    }

    public static WidgetPanel createBuildingPanel() {
        WidgetTable table = new WidgetTable();
        table.setCellPadding(3);

        int columns = 3;
        int pos = 0;
        for(Class<? extends IAntChamber> type : ChamberRegistry.getChamberTypes()) {
            if(type == EntranceChamber.class) {
                continue;
            }

            IAntChamber chamber = ChamberRegistry.getChamberInstance(type);
            WidgetBuyChamberButton button = new WidgetBuyChamberButton(chamber);

            int nextTier = ClientChamberGuiCache.maxTierLevel.getOrDefault(type, -1) + 1;
            if(nextTier < 0 || nextTier >= chamber.getTierList().size()) {
                continue;
            }

            IBlockState state = chamber.getTierList().get(nextTier);
            TextureAtlasSprite atlasSprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state);
            button.setAtlasSprite(atlasSprite);

            if(!chamber.shouldListInStore()) {
                continue;
            }

            double cost = chamber.tierCost(nextTier, chamber.getTierList().get(nextTier));
            if(getPropertyValue(TotalAnts.class) < cost) {
                button.setEnabled(false);
            }

            button.addTooltipLine(I18n.format("gui.ants.hill_chamber.infos.price", SmartNumberFormatter.formatNumber(cost)));

            button.addListener(MouseClickEvent.class, (event, widget) -> {
                ActionRegistry.fireHillAction(new BuyChamber(type));
                return WidgetEventResult.HANDLED;
            });

            int col = pos % columns;
            int row = pos / columns;
            table.add(col, row, button);

            pos++;
        }

        return table;
    }

    public static <V> IHillProperty<V> getProperty(Class<? extends IHillProperty<V>> propClass) {
        if(!properties.containsKey(propClass)) {
            try {
                IHillProperty property = propClass.newInstance();
                property.setValue(property.getDefault());
                properties.put(propClass, property);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return properties.get(propClass);
    }

    public static <V> V getPropertyValue(Class<? extends IHillProperty<V>> property) {
        return getProperty(property).getValue();
    }
}
