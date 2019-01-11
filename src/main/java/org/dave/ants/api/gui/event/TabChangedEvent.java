package org.dave.ants.api.gui.event;

import org.dave.ants.api.gui.widgets.WidgetPanel;

public class TabChangedEvent extends ValueChangedEvent<WidgetPanel> {
    public TabChangedEvent(WidgetPanel oldValue, WidgetPanel newValue) {
        super(oldValue, newValue);
    }
}
