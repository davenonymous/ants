package org.dave.ants.chambers.livingquarters;


import com.google.common.math.LongMath;
import net.minecraft.client.resources.I18n;
import org.dave.ants.api.chambers.AntChamber;
import org.dave.ants.api.gui.widgets.WidgetPanel;
import org.dave.ants.api.gui.widgets.composed.WidgetStatsTable;
import org.dave.ants.api.properties.calculated.MaxAnts;
import org.dave.ants.api.properties.stored.TotalAnts;
import org.dave.ants.chambers.UpgradeableChamber;
import org.dave.ants.gui.ClientChamberGuiCache;
import org.dave.ants.hills.HillData;
import org.dave.ants.util.SmartNumberFormatter;

@AntChamber
public class LivingQuarters extends UpgradeableChamber {
    public static final String CONFIG_CATEGORY = "Living Quarters";
    public static double priceToBuy = 10.0d;

    // Upgrade-Options
    public static int maxUpgrades = 10;
    public static double baseUpgradePrice = 10.0d;
    public static double upgradePriceMultiplier = 1.09d;

    @Override
    public String description() {
        return I18n.format("gui.ants.chamber.living_quarters.description");
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
    public boolean payPrice(HillData hillData) {
        if(hillData.getPropertyValue(TotalAnts.class) < priceToBuy) {
            return false;
        }

        hillData.modifyPropertyValue(TotalAnts.class, ants -> ants - priceToBuy);
        return true;
    }

    @Override
    public void applyHillModification(HillData data) {
        data.modifyPropertyValue(MaxAnts.class, ma -> ma + LongMath.pow(10, upgrades));
    }

    @Override
    public boolean hasGui() {
        return true;
    }

    @Override
    public WidgetPanel createGuiPanel() {
        WidgetPanel storagePanel = new WidgetPanel();
        storagePanel.add(getUpgradeBar());

        WidgetStatsTable statsTable = new WidgetStatsTable();
        statsTable.setX(1);
        statsTable.setY(25);

        long totalCapacityModifier = LongMath.pow(10, upgrades);
        double percentOfTotal = totalCapacityModifier / ClientChamberGuiCache.getPropertyValue(MaxAnts.class);

        statsTable.addStatistic(
                I18n.format("gui.ants.chamber.living_quarters.stats.beds"),
                SmartNumberFormatter.formatNumber(totalCapacityModifier)
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
        return 0x2655dc;
    }
}
