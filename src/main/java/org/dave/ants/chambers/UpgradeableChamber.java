package org.dave.ants.chambers;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import org.dave.ants.actions.ActionRegistry;
import org.dave.ants.actions.Upgrade;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.chambers.IChamberAction;
import org.dave.ants.api.gui.event.MouseClickEvent;
import org.dave.ants.api.gui.event.WidgetEventResult;
import org.dave.ants.api.gui.widgets.*;
import org.dave.ants.api.gui.widgets.composed.WidgetLabeledProgressBar;
import org.dave.ants.api.properties.stored.TotalAnts;
import org.dave.ants.api.serialization.Store;
import org.dave.ants.base.BaseNBTSerializable;
import org.dave.ants.hills.HillData;
import org.dave.ants.util.SmartNumberFormatter;

import static org.dave.ants.gui.ClientChamberGuiCache.getPropertyValue;

public abstract class UpgradeableChamber extends BaseNBTSerializable implements IAntChamber {
    @Store
    public int upgrades = 0;

    /**
     * How many times this chamber can be upgraded.
     *
     * @return maximum number of upgrades.
     */
    public abstract int getMaxUpgrades();

    /**
     * Base price for the first upgrade that can be bought.
     *
     * @return
     */
    public abstract double getBaseUpgradePrice();

    /**
     * Multiplier to the base value for each successive upgrade.
     * Calculated like this: baseprice * multiplier^upgrades
     *
     * Balanced values are between 1.07 and 1.15.
     * See https://gamedevelopment.tutsplus.com/articles/numbers-getting-bigger-the-design-and-math-of-incremental-games--cms-24023
     *
     * @return
     */
    public abstract double getUpgradePriceMultiplier();

    public double getUpgradePrice() {
        return Math.ceil(getBaseUpgradePrice() * Math.pow(getUpgradePriceMultiplier(), upgrades));
    }

    public Widget getUpgradeBar() {
        WidgetPanel panel = new WidgetPanel();
        panel.setId("UpgradePanel");
        panel.setWidth(165);
        panel.setHeight(21);

        int buttonWidth = 20;

        WidgetLabeledProgressBar upgradeBar = new WidgetLabeledProgressBar(I18n.format("gui.ants.upgradeable_chamber.upgrades.label"), 0, getMaxUpgrades(), (double)upgrades);
        upgradeBar.getMaxValueLabel().setX(165 - 5 - buttonWidth - upgradeBar.getMaxValueLabel().width);
        upgradeBar.getProgressBar().setForegroundColor(0xFF6666FF);
        upgradeBar.getProgressBar().setWidth(165 - 5 - buttonWidth);
        upgradeBar.getProgressBar().setDisplayMode(WidgetProgressBar.EnumDisplayMode.VALUE);

        panel.add(upgradeBar);

        WidgetButton button = new WidgetButton("+");
        button.setWidth(buttonWidth);
        button.setX(165 - buttonWidth - 1);
        button.setY(1);
        button.addListener(MouseClickEvent.class, (event, widget) -> {
            ActionRegistry.fireChamberAction(new Upgrade());
            return WidgetEventResult.HANDLED;
        });

        double totalAnts = getPropertyValue(TotalAnts.class);
        double price = getUpgradePrice();
        button.addTooltipLine(I18n.format("gui.ants.hill_chamber.infos.price", SmartNumberFormatter.formatNumber(price)));
        if(price > totalAnts) {
            button.setEnabled(false);
            button.addTooltipLine(I18n.format("gui.ants.hill_chamber.infos.need_more_ants"));
        }

        panel.add(button);

        panel.addTooltipLine(I18n.format("gui.ants.chamber.common.upgrade.tooltip"));
        return panel;
    }

    @Override
    public void onChamberAction(EntityPlayer player, IChamberAction action, HillData hillData) {
        if(action instanceof Upgrade) {
            double totalAnts = hillData.getPropertyValue(TotalAnts.class);
            double price = getUpgradePrice();

            if(price > totalAnts) {
                return;
            }

            this.upgrades++;
            hillData.modifyPropertyValue(TotalAnts.class, ants -> ants - price);

            this.markDirty();
            hillData.updateHillStatistics();
        }
    }
}
