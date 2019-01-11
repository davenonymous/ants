package org.dave.ants.chambers.queen;

import net.minecraft.client.resources.I18n;
import org.dave.ants.api.chambers.AntChamber;
import org.dave.ants.api.gui.widgets.Widget;
import org.dave.ants.api.gui.widgets.WidgetPanel;
import org.dave.ants.api.gui.widgets.composed.WidgetStatsTable;
import org.dave.ants.api.properties.calculated.MaxAnts;
import org.dave.ants.api.properties.calculated.TicksBetweenBabies;
import org.dave.ants.api.properties.calculated.TotalQueens;
import org.dave.ants.api.properties.stored.TotalAnts;
import org.dave.ants.chambers.WorkableChamber;
import org.dave.ants.gui.ClientChamberGuiCache;
import org.dave.ants.hills.HillData;
import org.dave.ants.util.SmartNumberFormatter;

@AntChamber
public class QueensChamber extends WorkableChamber {
    public static final String CONFIG_CATEGORY = "Queens Chamber";
    public static int ticksReducedPerWorker = 1;
    public static double priceToBuy = 250.0d;
    public static double minimumBedsToBeVisible = 200.0d;
    public static double priceForWorkers = 100.0d;
    public static int maxWorkers = 5;

    @Override
    public String description() {
        return I18n.format("gui.ants.chamber.queens_chamber.description");
    }

    @Override
    public boolean shouldListInStore() {
        return ClientChamberGuiCache.getPropertyValue(MaxAnts.class) >= minimumBedsToBeVisible;
    }

    @Override
    public String priceDescription() {
        return I18n.format("gui.ants.hill_chamber.infos.price", SmartNumberFormatter.formatNumber(priceToBuy));
    }

    @Override
    public boolean canBeBought() {
        return ClientChamberGuiCache.getPropertyValue(TotalAnts.class) >= priceToBuy;
    }

    @Override
    public boolean payPrice(HillData hillData) {
        if(hillData.getPropertyValue(TotalAnts.class) < priceToBuy) {
            return false;
        }

        hillData.modifyPropertyValue(TotalAnts.class, ants -> ants - priceToBuy);
        return true;
    }

    // TODO: Add ability to buy ants for food

    @Override
    public void applyHillModification(HillData data) {
        data.modifyPropertyValue(TotalQueens.class, totalQueens -> totalQueens + 1);
        data.modifyPropertyValue(TicksBetweenBabies.class, ticks -> ticks - ticksReducedPerWorker*workers);
    }

    @Override
    public boolean hasGui() {
        return true;
    }

    @Override
    public WidgetPanel createGuiPanel() {
        WidgetPanel storagePanel = new WidgetPanel();
        Widget workerBar = getWorkerBar();
        storagePanel.add(workerBar);

        WidgetStatsTable statsTable = new WidgetStatsTable();
        statsTable.setX(1);
        statsTable.setY(50);

        double thisGain = ticksReducedPerWorker * workers;

        statsTable.addStatistic(
                I18n.format("gui.ants.chamber.queens_chamber.stats.interval_reduced"),
                SmartNumberFormatter.formatNumber(thisGain / 20.0d) + "s"
        );

        storagePanel.add(statsTable);

        return storagePanel;
    }
    @Override
    public int antFillingColorTint() {
        return 0xEEEEEE;
    }

    @Override
    public int getMaxWorkers() {
        return maxWorkers;
    }

    @Override
    public double priceForWorkers(int count) {
        return priceForWorkers;
    }

    @Override
    public int getMaxUpgrades() {
        return 0;
    }

    @Override
    public double getBaseUpgradePrice() {
        return 0;
    }

    @Override
    public double getUpgradePriceMultiplier() {
        return 0;
    }
}
