package org.dave.ants.api.gui.ants;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import org.dave.ants.Ants;
import org.dave.ants.actions.BuyAnt;
import org.dave.ants.api.gui.event.MouseClickEvent;
import org.dave.ants.api.gui.event.ValueChangedEvent;
import org.dave.ants.api.gui.event.WidgetEventResult;
import org.dave.ants.api.gui.widgets.WidgetButton;
import org.dave.ants.api.gui.widgets.WidgetPanel;
import org.dave.ants.api.gui.widgets.WidgetProgressBar;
import org.dave.ants.api.properties.calculated.FoodGainPerTick;
import org.dave.ants.chambers.queen.QueensChamber;
import org.dave.ants.util.SmartNumberFormatter;

public class WidgetBuyAntsButton extends WidgetPanel {
    private WidgetProgressBar cooldownBar;
    private WidgetButton button;

    public WidgetBuyAntsButton() {
        this.setWidth(60);
        this.setHeight(75);

        button = new WidgetButton("") {
            @Override
            protected void drawButtonContent(GuiScreen screen, FontRenderer renderer) {
                super.drawButtonContent(screen, renderer);

                screen.mc.getRenderItem().renderItemAndEffectIntoGUI(Ants.chamberTypes.createItemStackForChamberType(QueensChamber.class, 0), (width - 16) / 2, 20 );
            }
        };

        IBlockState state = Blocks.DIRT.getDefaultState();
        TextureAtlasSprite atlasSprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state);

        button.setAtlasSprite(atlasSprite);
        button.setWidth(60);
        button.setHeight(60);

        button.addListener(MouseClickEvent.class, (event, widget) -> {
            cooldownBar.setValue(cooldownBar.getValue() + BuyAnt.ticksPerClick);

            Ants.actionRegistry.fireHillAction(new BuyAnt());
            return WidgetEventResult.CONTINUE_PROCESSING;
        });

        this.add(button);

        cooldownBar = new WidgetProgressBar();
        cooldownBar.setWidth(60);
        cooldownBar.setHeight(12);
        cooldownBar.setY(62);
        cooldownBar.setRangeMin(0.0d);
        cooldownBar.setRangeMax(BuyAnt.totalRange);
        cooldownBar.setDisplayMode(WidgetProgressBar.EnumDisplayMode.NOTHING);

        cooldownBar.addListener(ValueChangedEvent.class, (event, widget) -> {
            int colorIndex = BuyAnt.getIndex(cooldownBar.getValue());
            cooldownBar.setForegroundColor(BuyAnt.colors[colorIndex]);

            double price = Ants.clientHillData.getPropertyValue(FoodGainPerTick.class) * BuyAnt.price[colorIndex];
            double gain = BuyAnt.gain[colorIndex];

            this.setTooltipLines(
                    I18n.format("gui.ants.hill_chamber.infos.buy_ants"),
                    I18n.format("gui.ants.hill_chamber.infos.price_food", SmartNumberFormatter.formatNumber(price)),
                    I18n.format("gui.ants.hill_chamber.infos.gain", SmartNumberFormatter.formatNumber(gain))
            );

            return WidgetEventResult.CONTINUE_PROCESSING;
        });

        this.add(cooldownBar);
    }

    public double getCooldownTicks() {
        return this.cooldownBar.getValue();
    }

    public void setCooldownTicks(double value) {
        this.cooldownBar.setValue(value);
    }
}
