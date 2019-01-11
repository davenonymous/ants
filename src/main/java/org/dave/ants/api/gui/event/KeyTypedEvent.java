package org.dave.ants.api.gui.event;

public class KeyTypedEvent implements IEvent {
    public char typedChar;
    public int keyCode;

    public KeyTypedEvent(char typedChar, int keyCode) {
        this.typedChar = typedChar;
        this.keyCode = keyCode;
    }
}
