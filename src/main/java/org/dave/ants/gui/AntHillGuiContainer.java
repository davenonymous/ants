package org.dave.ants.gui;

import org.dave.ants.Ants;
import org.dave.ants.api.gui.WidgetGuiContainer;

public class AntHillGuiContainer extends WidgetGuiContainer {
    public static final int WIDTH = 210;
    public static final int HEIGHT = 183;

    public AntHillGuiContainer(AntHillContainer container) {
        super(container);

        this.xSize = WIDTH;
        this.ySize = HEIGHT;
        this.gui = new AntHillGUI(WIDTH, HEIGHT, container.world, container.pos);
        this.gui.hasTabs = true;
        this.gui.setId("AntHillGui");
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();

        this.resetMousePositions();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();

        Ants.clientHillData.reset();
    }
}
