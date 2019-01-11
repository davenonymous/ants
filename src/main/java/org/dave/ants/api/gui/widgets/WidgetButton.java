package org.dave.ants.api.gui.widgets;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.dave.ants.Ants;
import org.dave.ants.api.gui.event.MouseEnterEvent;
import org.dave.ants.api.gui.event.MouseExitEvent;
import org.dave.ants.api.gui.event.WidgetEventResult;
import org.dave.ants.gui.GUIHelper;


public class WidgetButton extends Widget {
    protected String unlocalizedLabel;
    public boolean hovered = false;
    public ResourceLocation backgroundTexture = new ResourceLocation("minecraft", "textures/blocks/stone.png");

    protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation(Ants.MODID, "textures/gui/tabicons.png");

    public WidgetButton(String unlocalizedLabel) {
        this.setHeight(20);
        this.setWidth(100);

        this.setId("Button[" + unlocalizedLabel + "]");
        this.unlocalizedLabel = unlocalizedLabel;

        this.addListener(MouseEnterEvent.class, (event, widget) -> {((WidgetButton)widget).hovered = true; return WidgetEventResult.CONTINUE_PROCESSING; });
        this.addListener(MouseExitEvent.class, (event, widget) -> {((WidgetButton)widget).hovered = false; return WidgetEventResult.CONTINUE_PROCESSING; });
    }

    public WidgetButton setBackgroundTexture(ResourceLocation backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
        return this;
    }

    public void setUnlocalizedLabel(String unlocalizedLabel) {
        this.unlocalizedLabel = unlocalizedLabel;
    }

    @Override
    public void draw(GuiScreen screen) {
        //Logz.info("Width: %d, height: %d", width, height);

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.translate(0.0f, 0.0f, 2.0f);

        // Draw the background
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if(!enabled) {
            GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
        } else if(hovered) {
            GlStateManager.color(0.55F, 0.65F, 1.0F, 1.0F);
        }
        screen.mc.getTextureManager().bindTexture(backgroundTexture);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, width, height, 16.0f, 16.0f);


        GlStateManager.color(1.0F, 1.0F, 1.0F, hovered ? 1.0F : 1.0F);
        screen.mc.getTextureManager().bindTexture(BUTTON_TEXTURES);

        // Top Left corner
        int texOffsetX = 64;
        int texOffsetY = 84;
        int overlayWidth = 20;

        screen.drawTexturedModalRect(0, 0, texOffsetX, texOffsetY, 4, 4);


        // Top right corner
        screen.drawTexturedModalRect(0+width - 4, 0, texOffsetX + overlayWidth - 4, texOffsetY, 4, 4);

        // Bottom Left corner
        screen.drawTexturedModalRect(0, this.height - 4, texOffsetX, texOffsetY + overlayWidth - 4, 4, 4);

        // Bottom Right corner
        screen.drawTexturedModalRect(0+width - 4, this.height - 4, texOffsetX + overlayWidth - 4, texOffsetY + overlayWidth - 4, 4, 4);


        // Top edge
        GUIHelper.drawStretchedTexture(0+4, 0, width - 8, 4, texOffsetX + 4, texOffsetY, 12, 4);

        // Bottom edge
        GUIHelper.drawStretchedTexture(0+4, this.height - 4, width - 8, 4, texOffsetX + 4, texOffsetY + overlayWidth - 4, 12, 4);

        // Left edge
        GUIHelper.drawStretchedTexture(0, 4, 4, this.height - 8, texOffsetX, texOffsetY+4, 4, 12);

        // Right edge
        GUIHelper.drawStretchedTexture(0+width - 4, 4, 4, this.height - 8, texOffsetX + overlayWidth - 4, texOffsetY + 3, 4, 12);


        FontRenderer fontrenderer = screen.mc.fontRenderer;
        drawButtonContent(screen, fontrenderer);

        GlStateManager.popMatrix();
    }

    protected void drawButtonContent(GuiScreen screen, FontRenderer renderer) {
        drawString(screen, renderer);
    }

    protected void drawString(GuiScreen screen, FontRenderer renderer) {
        int color = 0xEEEEEE;
        screen.drawCenteredString(renderer, unlocalizedLabel, width / 2, (height - 8) / 2, color);
    }
}
