package org.dave.ants.chambers.foraging;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import org.dave.ants.Ants;
import org.dave.ants.api.chambers.AntChamber;
import org.dave.ants.api.gui.widgets.Widget;
import org.dave.ants.api.gui.widgets.WidgetPanel;
import org.dave.ants.api.gui.ants.WidgetStatsTable;
import org.dave.ants.api.properties.calculated.FoodGainPerTick;
import org.dave.ants.chambers.WorkableChamber;
import org.dave.ants.config.GeneralAntHillConfig;
import org.dave.ants.hills.HillData;
import org.dave.ants.util.SmartNumberFormatter;

import java.util.List;

@AntChamber
public class ForagingGrounds extends WorkableChamber {
    public static final String CONFIG_CATEGORY = "Foraging Grounds";
    //public static double priceToBuy = 20.0d;

    public static double upgradeProductionIncreaseMultiplier = 0.05d;

    // Worker-Options
    public static int workersPerUpgrade = 5;
    public static double baseWorkerPrice = 20.0d;
    public static double workerPriceMultiplier = 1.07d;

    // Upgrade-Options
    public static int maxUpgrades = 100;
    public static double baseUpgradePrice = 20.0d;
    public static double upgradePriceMultiplier = 1.10d;

    // Tier-Scaling options
    public static double baseFoodGainPerTick = 5;

    @Override
    public String description() {
        return I18n.format("gui.ants.chamber.foraging_grounds.description");
    }

    @Override
    public boolean shouldListInStore() {
        return true;
    }


    @Override
    public void applyHillModification(HillData data, int chamberTier) {
        double baseValue = baseFoodGainPerTick * GeneralAntHillConfig.defaultTierIncomeRate.get(chamberTier);
        double upgradedFoodGain = baseValue * Math.pow(GeneralAntHillConfig.defaultUpgradeMultiplier, upgrades);
        double workedFoodGain = upgradedFoodGain * Math.pow(GeneralAntHillConfig.defaultWorkerMultiplier, workers);

        data.modifyPropertyValue(FoodGainPerTick.class, gain -> gain + workedFoodGain);
    }


    @Override
    public boolean hasGui() {
        return true;
    }

    @Override
    public WidgetPanel createGuiPanel(int tier) {
        WidgetPanel storagePanel = new WidgetPanel();
        Widget upgradeBar = getUpgradeBar();
        storagePanel.add(upgradeBar);

        Widget workerBar = getWorkerBar();
        workerBar.setY(25);
        storagePanel.add(workerBar);

        WidgetStatsTable statsTable = new WidgetStatsTable();
        statsTable.setX(1);
        statsTable.setY(75);

        double baseValue = baseFoodGainPerTick * GeneralAntHillConfig.defaultTierIncomeRate.get(tier);
        double upgradedFoodGain = baseValue * Math.pow(GeneralAntHillConfig.defaultUpgradeMultiplier, upgrades);
        double workedFoodGain = upgradedFoodGain * Math.pow(GeneralAntHillConfig.defaultWorkerMultiplier, workers);

        double percentOfTotal = workedFoodGain / Ants.clientHillData.getPropertyValue(FoodGainPerTick.class);

        statsTable.addStatistic(
                I18n.format("gui.ants.chamber.common.worker.stats.worker_production"),
                SmartNumberFormatter.formatNumber(workedFoodGain*20) + "/s"
        );

        statsTable.addStatistic(
                I18n.format("gui.ants.chamber.common.stats.percent_of_total"),
                String.format("%.1f%%", percentOfTotal * 100)
        );


        storagePanel.add(statsTable);

        return storagePanel;
    }

    @Override
    public List<IBlockState> getTierList() {
        return GeneralAntHillConfig.defaultTierList;
    }

    @Override
    public double tierCost(int tier, IBlockState state) {
        return GeneralAntHillConfig.defaultTierCost.get(tier);
    }


    @Override
    public double getMaxWorkers() {
        return workersPerUpgrade * (upgrades+1);
    }

    @Override
    public double getBaseWorkerPrice() {
        return baseWorkerPrice;
    }

    @Override
    public double getWorkerPriceMultiplier() {
        return workerPriceMultiplier;
    }

    @Override
    public double getMaxUpgrades() {
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
    public int antFillingColorTint() {
        return 0x399d36;
    }
}
