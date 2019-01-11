package org.dave.ants.api.gui.widgets.composed;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.dave.ants.api.gui.widgets.WidgetPanel;
import org.dave.ants.api.gui.widgets.WidgetProgressBar;
import org.dave.ants.api.gui.widgets.WidgetTextBox;
import org.dave.ants.util.SmartNumberFormatter;

public class WidgetLabeledProgressBar extends WidgetPanel {
    WidgetTextBox label;
    WidgetProgressBar bar;
    WidgetTextBox maxValueLabel;

    public WidgetLabeledProgressBar(String label, double rangeMin, double rangeMax, double value) {
        this.label = new WidgetTextBox(label);
        this.label.setTextColor(0xFF333333);
        this.label.setWidth(80);
        this.label.setHeight(12);
        this.add(this.label);

        this.bar = new WidgetProgressBar();
        this.bar.setY(9);
        this.bar.setRangeMin(rangeMin);
        this.bar.setRangeMax(rangeMax);
        this.bar.setValue(value);
        this.bar.setWidth(165);
        this.bar.setHeight(12);
        this.bar.setDisplayMode(WidgetProgressBar.EnumDisplayMode.VALUE_AND_PERCENTAGE);
        this.add(this.bar);

        String rangeMaxString = I18n.format("gui.ants.hill_chamber.infos.max_label_value", SmartNumberFormatter.formatNumber(rangeMax));
        int stringWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(rangeMaxString);
        this.maxValueLabel = new WidgetTextBox(rangeMaxString);
        this.maxValueLabel.setWidth(stringWidth);
        this.maxValueLabel.setX(width - stringWidth);
        this.maxValueLabel.setTextColor(0xFF333333);
        this.add(this.maxValueLabel);
    }

    public WidgetTextBox getLabel() {
        return label;
    }

    public WidgetProgressBar getProgressBar() {
        return bar;
    }

    public WidgetTextBox getMaxValueLabel() {
        return maxValueLabel;
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        this.maxValueLabel.setX(width - this.maxValueLabel.width);
    }
}
