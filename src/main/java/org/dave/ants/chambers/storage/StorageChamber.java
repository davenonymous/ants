package org.dave.ants.chambers.storage;

import com.google.common.math.LongMath;
import net.minecraft.client.resources.I18n;
import org.dave.ants.api.chambers.AntChamber;
import org.dave.ants.api.gui.widgets.WidgetPanel;
import org.dave.ants.api.gui.widgets.WidgetTextBox;
import org.dave.ants.api.properties.calculated.FoodCapacity;
import org.dave.ants.api.properties.stored.TotalAnts;
import org.dave.ants.chambers.UpgradeableChamber;
import org.dave.ants.gui.ClientChamberGuiCache;
import org.dave.ants.hills.HillData;
import org.dave.ants.util.SmartNumberFormatter;

@AntChamber
public class StorageChamber extends UpgradeableChamber {
    public static final String CONFIG_CATEGORY = "Storage Chamber";
    public static double priceToBuy = 10.0d;

    // Upgrade-Options
    public static int maxUpgrades = 15;
    public static double baseUpgradePrice = 10.0d;
    public static double upgradePriceMultiplier = 1.09d;

    @Override
    public String description() {
        return I18n.format("gui.ants.chamber.storage_chamber.description");
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
    public void applyHillModification(HillData data) {
        data.modifyPropertyValue(FoodCapacity.class, fc -> fc + LongMath.pow(10, upgrades+2));
    }

    @Override
    public boolean hasGui() {
        return true;
    }

    @Override
    public WidgetPanel createGuiPanel() {
        WidgetPanel storagePanel = new WidgetPanel();
        storagePanel.add(getUpgradeBar());

        long totalCapacityModifier = LongMath.pow(10, upgrades+2);

        WidgetTextBox capacityTextBox = new WidgetTextBox(I18n.format("gui.ants.chamber.storage_chamber.space", SmartNumberFormatter.formatNumber(totalCapacityModifier)));
        capacityTextBox.setTextColor(0xFF333333);
        capacityTextBox.setWidth(120);
        capacityTextBox.setHeight(18);
        capacityTextBox.setY(25);
        storagePanel.add(capacityTextBox);

        return storagePanel;
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
    public int antFillingColorTint() {
        return 0xd9ac85;
    }
}
