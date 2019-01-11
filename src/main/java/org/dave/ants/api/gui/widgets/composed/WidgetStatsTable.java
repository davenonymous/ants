package org.dave.ants.api.gui.widgets.composed;

import net.minecraft.client.Minecraft;
import org.dave.ants.api.gui.widgets.WidgetTable;
import org.dave.ants.api.gui.widgets.WidgetTextBox;

public class WidgetStatsTable extends WidgetTable {
    private int labelTextColor = 0x333333;
    private int valueTextColor = 0x444444;
    private int rowHeight = 14;
    private int extraLabelWidth = 10;

    public WidgetStatsTable setLabelTextColor(int labelTextColor) {
        this.labelTextColor = labelTextColor;
        return this;
    }

    public WidgetStatsTable setValueTextColor(int valueTextColor) {
        this.valueTextColor = valueTextColor;
        return this;
    }

    public WidgetStatsTable setRowHeight(int rowHeight) {
        this.rowHeight = rowHeight;
        return this;
    }

    public WidgetStatsTable setValuePadding(int extraLabelWidth) {
        this.extraLabelWidth = extraLabelWidth;
        return this;
    }

    public void addStatistic(String label, String value, int customValueColor) {
        int rowToAdd = getRowCount();

        WidgetTextBox labelWidget = new WidgetTextBox(label, labelTextColor);
        labelWidget.setWidth(Minecraft.getMinecraft().fontRenderer.getStringWidth(label) + extraLabelWidth);
        labelWidget.setHeight(rowHeight);

        WidgetTextBox valueWidget = new WidgetTextBox(value, customValueColor == -1 ? valueTextColor : customValueColor);
        valueWidget.setWidth(Minecraft.getMinecraft().fontRenderer.getStringWidth(value));
        valueWidget.setHeight(rowHeight);

        this.add(0, rowToAdd, labelWidget);
        this.add(1, rowToAdd, valueWidget);
    }

    public void addStatistic(String label, String value) {
        addStatistic(label, value, -1);
    }
}
