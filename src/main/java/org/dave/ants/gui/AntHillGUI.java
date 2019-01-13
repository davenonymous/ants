package org.dave.ants.gui;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dave.ants.Ants;
import org.dave.ants.actions.BuyChamber;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.gui.GUI;
import org.dave.ants.api.gui.event.MouseClickEvent;
import org.dave.ants.api.gui.event.TabChangedEvent;
import org.dave.ants.api.gui.event.UpdateScreenEvent;
import org.dave.ants.api.gui.event.WidgetEventResult;
import org.dave.ants.api.gui.widgets.WidgetPanel;
import org.dave.ants.api.gui.widgets.WidgetTable;
import org.dave.ants.api.gui.widgets.WidgetTabsPanel;
import org.dave.ants.api.gui.widgets.composed.WidgetBuyChamberButton;
import org.dave.ants.api.gui.widgets.composed.WidgetLabeledProgressBar;
import org.dave.ants.api.properties.calculated.*;
import org.dave.ants.api.properties.stored.StoredFood;
import org.dave.ants.api.properties.stored.TotalAnts;
import org.dave.ants.chambers.entrance.EntranceChamber;
import org.dave.ants.init.Blockss;
import org.dave.ants.tiles.BaseHillTile;
import org.dave.ants.util.SmartNumberFormatter;

import java.util.Collections;

public class AntHillGUI extends GUI {
    private int activeTab = -1;
    private long guiCreatedTimestamp = Long.MIN_VALUE;

    private World world;
    private BlockPos pos;
    private int chamberTier = 0;

    public AntHillGUI(int width, int height, World world, BlockPos pos) {
        super(0, 0, width, height);
        this.world = world;
        this.pos = pos;

        TileEntity tileEntity = this.world.getTileEntity(this.pos);
        if(tileEntity != null && tileEntity instanceof BaseHillTile) {
            BaseHillTile baseHillTile = (BaseHillTile) tileEntity;
            this.chamberTier = baseHillTile.getChamberTier();
        }

        this.addListener(UpdateScreenEvent.class, (event, widget) -> {
            if(!Ants.clientHillData.hasData()) {
                return WidgetEventResult.CONTINUE_PROCESSING;
            }

            if(Ants.clientHillData.lastMessageReceived <= guiCreatedTimestamp) {
                return WidgetEventResult.CONTINUE_PROCESSING;
            }

            this.recreateGui();

            return WidgetEventResult.CONTINUE_PROCESSING;
        });
    }

    public void recreateGui() {
        this.clear();

        WidgetTabsPanel tabs = new WidgetTabsPanel();
        tabs.setX(32);
        tabs.setY(2);
        tabs.setWidth(width);
        tabs.setHeight(height);

        WidgetPanel hillInfos = createHillInfoPanel();
        hillInfos.setWidth(width - 2*5 - 32);
        hillInfos.setHeight(height - 2*5);
        hillInfos.setX(5);
        hillInfos.setY(5);
        hillInfos.setId("HillInfos");
        tabs.addPage(hillInfos, new ItemStack(Blockss.hillEntrance), Collections.singletonList(I18n.format("gui.ants.hill_chamber.tabs.stats.tooltip")));

        WidgetPanel buildingPanel = createBuildingPanel();
        buildingPanel.setWidth(width - 2*5 - 32);
        buildingPanel.setHeight(height - 2*5);
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

        IAntChamber clientChamberInstance = Ants.clientHillData.getChamberInstance();
        if(clientChamberInstance.hasGui()) {
            WidgetPanel chamberPanel = clientChamberInstance.createGuiPanel(chamberTier);
            chamberPanel.setWidth(width);
            chamberPanel.setHeight(height);
            chamberPanel.setX(5);
            chamberPanel.setY(5);
            chamberPanel.setId("ChamberPanel");

            ItemStack stack = Ants.chamberTypes.createItemStackForChamberType(Ants.clientHillData.chamberType);
            tabs.addPage(chamberPanel, stack, Collections.singletonList(I18n.format(stack.getTranslationKey() + ".name")));

            if(activeTab == -1) {
                activeTab = 2;
            }
        }

        tabs.setActivePage(activeTab);

        this.add(tabs);

        WidgetPanel buttonPanel = tabs.getButtonsPanel();
        buttonPanel.setId("ButtonPanel");
        buttonPanel.setX(0);
        buttonPanel.setY(0);
        buttonPanel.setWidth(40);
        buttonPanel.setHeight(80);

        this.add(buttonPanel);

        guiCreatedTimestamp = Ants.clientHillData.lastMessageReceived;
    }


    public WidgetPanel createHillInfoPanel() {
        WidgetPanel hillInfos = new WidgetPanel();

        WidgetLabeledProgressBar totalAnts = new WidgetLabeledProgressBar(I18n.format("gui.ants.hill_chamber.tabs.stats.total_ants"), 0, Ants.clientHillData.getPropertyValue(MaxAnts.class), Ants.clientHillData.getPropertyValue(TotalAnts.class));
        totalAnts.setWidth(165);
        totalAnts.setHeight(20);
        totalAnts.setY(0);
        totalAnts.addTooltipLine(I18n.format("gui.ants.hill_chamber.stats.total_ants.tooltip.antsperhatching", SmartNumberFormatter.formatNumber(Ants.clientHillData.getPropertyValue(AntsBornPerHatching.class))));
        totalAnts.addTooltipLine(I18n.format("gui.ants.hill_chamber.stats.total_ants.tooltip.interval", SmartNumberFormatter.formatNumber((double)Ants.clientHillData.getPropertyValue(TicksBetweenBabies.class) / 20.0)));
        hillInfos.add(totalAnts);

        WidgetLabeledProgressBar storedFood = new WidgetLabeledProgressBar(I18n.format("gui.ants.hill_chamber.tabs.stats.stored_food"), 0, Ants.clientHillData.getPropertyValue(FoodCapacity.class), Ants.clientHillData.getPropertyValue(StoredFood.class));
        storedFood.setWidth(165);
        storedFood.setHeight(20);
        storedFood.setY(25);

        double eaten = Ants.clientHillData.getPropertyValue(FoodRequirementPerAnt.class) * Ants.clientHillData.getPropertyValue(TotalAnts.class) * 20;
        double gathered = Ants.clientHillData.getPropertyValue(FoodGainPerTick.class) * 20;
        double total =  gathered - eaten;

        storedFood.addTooltipLine(I18n.format("gui.ants.hill_chamber.stats.stored_food.tooltip.gathered", SmartNumberFormatter.formatNumber(gathered)));
        storedFood.addTooltipLine(I18n.format("gui.ants.hill_chamber.stats.stored_food.tooltip.eaten", SmartNumberFormatter.formatNumber(eaten)));
        storedFood.addTooltipLine(I18n.format("gui.ants.hill_chamber.stats.stored_food.tooltip.total", SmartNumberFormatter.formatNumber(total)));

        hillInfos.add(storedFood);

        // TODO: Add ability to buy ants for food

        return hillInfos;
    }

    public WidgetPanel createBuildingPanel() {
        WidgetTable table = new WidgetTable();
        table.setCellPadding(3);

        int columns = 3;
        int pos = 0;
        for(Class<? extends IAntChamber> type : Ants.chamberTypes.getChamberTypes()) {
            if(type == EntranceChamber.class) {
                continue;
            }

            IAntChamber chamber = Ants.chamberTypes.getChamberInstance(type);
            WidgetBuyChamberButton button = new WidgetBuyChamberButton(chamber);

            int nextTier = Ants.clientHillData.maxTierLevel.getOrDefault(type, -1) + 1;
            if(nextTier < 0 || nextTier >= chamber.getTierList().size()) {
                continue;
            }

            IBlockState state = chamber.getTierList().get(nextTier);
            TextureAtlasSprite atlasSprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state);
            button.setAtlasSprite(atlasSprite);

            if(!chamber.shouldListInStore()) {
                continue;
            }

            double cost = chamber.tierCost(nextTier, chamber.getTierList().get(nextTier));
            if(Ants.clientHillData.getPropertyValue(TotalAnts.class) < cost) {
                button.setEnabled(false);
            }

            button.addTooltipLine(I18n.format("gui.ants.hill_chamber.infos.price", SmartNumberFormatter.formatNumber(cost)));

            button.addListener(MouseClickEvent.class, (event, widget) -> {
                Ants.actionRegistry.fireHillAction(new BuyChamber(type));
                return WidgetEventResult.HANDLED;
            });

            int col = pos % columns;
            int row = pos / columns;
            table.add(col, row, button);

            pos++;
        }

        return table;
    }
}
