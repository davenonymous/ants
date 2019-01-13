package org.dave.ants.chambers.queen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import org.dave.ants.api.chambers.AntChamber;
import org.dave.ants.api.gui.widgets.Widget;
import org.dave.ants.api.gui.widgets.WidgetPanel;
import org.dave.ants.api.gui.widgets.composed.WidgetStatsTable;
import org.dave.ants.api.properties.calculated.AntsBornPerHatching;
import org.dave.ants.api.properties.calculated.TicksBetweenBabies;
import org.dave.ants.chambers.WorkableChamber;
import org.dave.ants.config.GeneralAntHillConfig;
import org.dave.ants.hills.HillData;
import org.dave.ants.util.SmartNumberFormatter;

import java.util.List;

@AntChamber
public class QueensChamber extends WorkableChamber {
    public static final String CONFIG_CATEGORY = "Queens Chamber";
    public static int ticksReducedPerWorker = 1;
    //public static double priceToBuy = 250.0d;
    public static double minimumBedsToBeVisible = 200.0d;

    // Worker-Options
    public static int workersPerUpgrade = 5;
    public static double baseWorkerPrice = 20.0d;
    public static double workerPriceMultiplier = 1.07d;

    // Upgrade-Options
    public static int maxUpgrades = 100;
    public static double baseUpgradePrice = 20.0d;
    public static double upgradePriceMultiplier = 1.10d;

    // Tier-Scaling options
    public static double baseExtraAnts = 20;


    @Override
    public String description() {
        return I18n.format("gui.ants.chamber.queens_chamber.description");
    }

    @Override
    public boolean shouldListInStore() {
        return true; //ClientHillData.getPropertyValue(MaxAnts.class) >= minimumBedsToBeVisible;
    }


    @Override
    public void applyHillModification(HillData data, int chamberTier) {
        double baseValue = baseExtraAnts * GeneralAntHillConfig.defaultTierIncomeRate.get(chamberTier);
        double upgradedExtraAnts = baseValue * Math.pow(GeneralAntHillConfig.defaultUpgradeMultiplier, upgrades);

        data.modifyPropertyValue(AntsBornPerHatching.class, antsBorn -> antsBorn + upgradedExtraAnts);
        data.modifyPropertyValue(TicksBetweenBabies.class, ticks -> ticks - ticksReducedPerWorker*workers);
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

        double thisGain = ticksReducedPerWorker * workers;

        statsTable.addStatistic(
                I18n.format("gui.ants.chamber.queens_chamber.stats.interval_reduced"),
                SmartNumberFormatter.formatNumber(thisGain / 20.0d) + "s"
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
        return 0xEEEEEE;
    }
}
