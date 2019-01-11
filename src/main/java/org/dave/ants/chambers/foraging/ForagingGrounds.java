package org.dave.ants.chambers.foraging;

import net.minecraft.client.resources.I18n;
import org.dave.ants.api.chambers.AntChamber;
import org.dave.ants.api.gui.widgets.Widget;
import org.dave.ants.api.gui.widgets.WidgetPanel;
import org.dave.ants.api.gui.widgets.composed.WidgetStatsTable;
import org.dave.ants.api.properties.calculated.FoodGainPerTick;
import org.dave.ants.api.properties.stored.TotalAnts;
import org.dave.ants.chambers.WorkableChamber;
import org.dave.ants.gui.ClientChamberGuiCache;
import org.dave.ants.hills.HillData;
import org.dave.ants.util.SmartNumberFormatter;

@AntChamber
public class ForagingGrounds extends WorkableChamber {
    public static final String CONFIG_CATEGORY = "Foraging Grounds";
    public static double priceToBuy = 20.0d;
    public static double upgradeProductionIncreaseMultiplier = 0.05d;

    // Worker-Options
    public static double priceForWorkers = 20.0d;
    public static int workersPerUpgrade = 5;

    // Upgrade-Options
    public static int maxUpgrades = 10;
    public static double baseUpgradePrice = 20.0d;
    public static double upgradePriceMultiplier = 1.10d;

    @Override
    public String description() {
        return I18n.format("gui.ants.chamber.foraging_grounds.description");
    }

    @Override
    public boolean shouldListInStore() {
        return true;
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

    @Override
    public int getMaxWorkers() {
        return workersPerUpgrade * (upgrades+1);
    }

    @Override
    public double priceForWorkers(int count) {
        return priceForWorkers * count;
    }


    @Override
    public int getMaxUpgrades() {
        return maxUpgrades;
    }

    @Override
    public double getBaseUpgradePrice() {
        return baseUpgradePrice;
    }

    @Override
    public double getUpgradePriceMultiplier() {
        return upgradePriceMultiplier;
    }

    @Override
    public void applyHillModification(HillData data) {
        data.modifyPropertyValue(FoodGainPerTick.class, gain -> gain + (upgradeProductionIncreaseMultiplier *(upgrades+1))*workers);
    }

    @Override
    public boolean hasGui() {
        return true;
    }

    @Override
    public WidgetPanel createGuiPanel() {
        WidgetPanel storagePanel = new WidgetPanel();
        Widget upgradeBar = getUpgradeBar();
        storagePanel.add(upgradeBar);

        Widget workerBar = getWorkerBar();
        workerBar.setY(25);
        storagePanel.add(workerBar);

        WidgetStatsTable statsTable = new WidgetStatsTable();
        statsTable.setX(1);
        statsTable.setY(75);

        double workerProduction = upgradeProductionIncreaseMultiplier * (upgrades+1);
        double thisGain = workerProduction * workers;
        double percentOfTotal = thisGain / ClientChamberGuiCache.getPropertyValue(FoodGainPerTick.class);

        statsTable.addStatistic(
                I18n.format("gui.ants.chamber.common.worker.stats.worker_production"),
                SmartNumberFormatter.formatNumber(workerProduction*20) + "/s"
        );
        statsTable.addStatistic(
                I18n.format("gui.ants.chamber.foraging_grounds.stats.food_gathered"),
                SmartNumberFormatter.formatNumber(thisGain*20) + "/s"
        );
        statsTable.addStatistic(
                I18n.format("gui.ants.chamber.common.stats.percent_of_total"),
                String.format("%.1f%%", percentOfTotal * 100)
        );


        storagePanel.add(statsTable);

        return storagePanel;
    }

    @Override
    public int antFillingColorTint() {
        return 0x399d36;
    }
}
