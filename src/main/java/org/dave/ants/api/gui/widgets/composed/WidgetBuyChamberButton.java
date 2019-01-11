package org.dave.ants.api.gui.widgets.composed;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.gui.widgets.WidgetButton;
import org.dave.ants.chambers.ChamberRegistry;
import org.dave.ants.gui.GUIHelper;

public class WidgetBuyChamberButton extends WidgetButton {
    private IAntChamber chamber;

    private static String getTranslatedChamberName(IAntChamber chamber) {
        return I18n.format(String.format("tile.ants.hill_chamber.%s.name", chamber.getClass().getSimpleName().toLowerCase()));
    }

    public WidgetBuyChamberButton(IAntChamber chamber) {
        super(getTranslatedChamberName(chamber));
        this.chamber = chamber;

        this.setWidth(50);
        this.setHeight(40);
        this.addTooltipLine(chamber.description());

        String priceDescription = chamber.priceDescription();
        if(priceDescription != null && priceDescription.length() > 0) {
            this.addTooltipLine(priceDescription);
        }
    }

    @Override
    protected void drawButtonContent(GuiScreen screen, FontRenderer renderer) {
        super.drawButtonContent(screen, renderer);

        screen.mc.getRenderItem().renderItemAndEffectIntoGUI(ChamberRegistry.createItemStackForChamberType(this.chamber.getClass()), (width - 16) / 2, 3 );
    }

    @Override
    protected void drawString(GuiScreen screen, FontRenderer renderer) {
        int color = 0xEEEEEE;
        GUIHelper.drawSplitStringCentered(unlocalizedLabel, screen, 2, 18, width-4, color);
        //renderer.drawSplitString(unlocalizedLabel, 2, 18, width-4, color);
    }
}
