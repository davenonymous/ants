package org.dave.ants.api.gui.event;


import org.dave.ants.api.gui.widgets.Widget;

public interface IWidgetListener<T extends IEvent> {
    WidgetEventResult call(T event, Widget widget);
}
