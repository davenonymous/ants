package org.dave.ants.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.gui.GUI;
import org.dave.ants.api.gui.WidgetGuiContainer;
import org.dave.ants.api.gui.event.TabChangedEvent;
import org.dave.ants.api.gui.event.WidgetEventResult;
import org.dave.ants.api.gui.widgets.WidgetPanel;
import org.dave.ants.api.gui.widgets.WidgetTabsPanel;
import org.dave.ants.api.properties.stored.TotalAnts;
import org.dave.ants.chambers.ChamberRegistry;
import org.dave.ants.init.Blockss;
import org.dave.ants.tiles.BaseHillTile;
import org.dave.ants.util.Logz;

import java.util.Collections;

public class AntHillGuiContainer extends WidgetGuiContainer {
    public static final int WIDTH = 210;
    public static final int HEIGHT = 183;

    private long guiCreatedTimestamp = Long.MIN_VALUE;

    int activeTab = -1;

    private World world;
    private BlockPos pos;
    private BaseHillTile baseHillTile;

    public AntHillGuiContainer(AntHillContainer container) {
        super(container);
        this.world = container.world;
        this.pos = container.pos;

        this.xSize = WIDTH;
        this.ySize = HEIGHT;
        this.gui = instantiateGui();

        TileEntity tileEntity = this.world.getTileEntity(this.pos);
        if(tileEntity != null && tileEntity instanceof BaseHillTile) {
            baseHillTile = (BaseHillTile) tileEntity;
        }
    }

    private GUI instantiateGui() {
        GUI gui = new GUI(0, 0, WIDTH, HEIGHT);
        gui.hasTabs = true;
        gui.setId("gui");
        return gui;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        if(!ClientChamberGuiCache.hasData()) {
            return;
        }

        if(ClientChamberGuiCache.lastMessageReceived <= guiCreatedTimestamp) {
            return;
        }

        this.gui.clear();

        int tier = 0;
        if(baseHillTile != null) {
            tier = baseHillTile.getChamberTier();
        }

        WidgetTabsPanel tabs = new WidgetTabsPanel();
        tabs.setX(32);
        tabs.setY(2);
        tabs.setWidth(WIDTH);
        tabs.setHeight(HEIGHT);

        WidgetPanel hillInfos = ClientChamberGuiCache.createHillInfoPanel();
        hillInfos.setWidth(WIDTH - 2*5 - 32);
        hillInfos.setHeight(HEIGHT - 2*5);
        hillInfos.setX(5);
        hillInfos.setY(5);
        hillInfos.setId("HillInfos");
        tabs.addPage(hillInfos, new ItemStack(Blockss.hillEntrance), Collections.singletonList(I18n.format("gui.ants.hill_chamber.tabs.stats.tooltip")));

        WidgetPanel buildingPanel = ClientChamberGuiCache.createBuildingPanel();
        buildingPanel.setWidth(WIDTH - 2*5 - 32);
        buildingPanel.setHeight(HEIGHT - 2*5);
        buildingPanel.setX(5);
        buildingPanel.setY(5);
        buildingPanel.setId("BuildingPanel");
        tabs.addPage(buildingPanel, new ItemStack(Items.EMERALD), Collections.singletonList(I18n.format("gui.ants.hill_chamber.tabs.buy.tooltip")));

        tabs.addListener(TabChangedEvent.class, (event, widget) -> {
            if(event.newValue == hillInfos) {
                activeTab = 0;
            } else if(event.newValue == buildingPanel) {
                activeTab = 1;
            } else {
                activeTab = 2;
            }

            return WidgetEventResult.CONTINUE_PROCESSING;
        });

        IAntChamber clientChamberInstance = ClientChamberGuiCache.getChamberInstance();
        if(clientChamberInstance.hasGui()) {
            WidgetPanel chamberPanel = clientChamberInstance.createGuiPanel(tier);
            chamberPanel.setWidth(WIDTH);
            chamberPanel.setHeight(HEIGHT);
            chamberPanel.setX(5);
            chamberPanel.setY(5);
            chamberPanel.setId("ChamberPanel");

            ItemStack stack = ChamberRegistry.createItemStackForChamberType(ClientChamberGuiCache.chamberType);
            tabs.addPage(chamberPanel, stack, Collections.singletonList(I18n.format(stack.getTranslationKey() + ".name")));

            if(activeTab == -1) {
                activeTab = 2;
            }
        }

        tabs.setActivePage(activeTab);

        this.gui.add(tabs);

        WidgetPanel buttonPanel = tabs.getButtonsPanel();
        buttonPanel.setId("ButtonPanel");
        buttonPanel.setX(0);
        buttonPanel.setY(0);
        buttonPanel.setWidth(40);
        buttonPanel.setHeight(80);

        this.gui.add(buttonPanel);
        this.resetMousePositions();

        guiCreatedTimestamp = ClientChamberGuiCache.lastMessageReceived;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();

        ClientChamberGuiCache.reset();
    }
}
