package org.dave.ants.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class GUIHelper {
    public static void drawSplitStringCentered(String str, GuiScreen screen, int x, int y, int width, int color) {
        FontRenderer renderer = screen.mc.fontRenderer;
        int yOffset = 0;
        for(String row : renderer.listFormattedStringToWidth(str, width)) {
            screen.drawCenteredString(renderer, row, x + width/2, y + yOffset, color);
            yOffset += renderer.FONT_HEIGHT;
        }
    }

    public static void drawStretchedTexture(int x, int y, int width, int height, int textureX, int textureY, int textureWidth, int textureHeight) {
        float f =  0.00390625F;
        double zLevel = 0.0f;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder
                .pos((double)(x + 0), (double)(y + height), zLevel)
                .tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + textureHeight) * f))
                .endVertex();

        bufferbuilder
                .pos((double)(x + width), (double)(y + height), zLevel)
                .tex((double)((float)(textureX + textureWidth) * f), (double)((float)(textureY + textureHeight) * f))
                .endVertex();

        bufferbuilder
                .pos((double)(x + width), (double)(y + 0), zLevel)
                .tex((double)((float)(textureX + textureWidth) * f), (double)((float)(textureY + 0) * f))
                .endVertex();

        bufferbuilder
                .pos((double)(x + 0), (double)(y + 0), zLevel)
                .tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f))
                .endVertex();

        tessellator.draw();
    }
}
