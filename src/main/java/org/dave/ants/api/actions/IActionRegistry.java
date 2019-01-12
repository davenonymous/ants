package org.dave.ants.api.actions;

public interface IActionRegistry {
    void fireChamberAction(IAntGuiAction action);

    void fireHillAction(IAntGuiAction action);
}
