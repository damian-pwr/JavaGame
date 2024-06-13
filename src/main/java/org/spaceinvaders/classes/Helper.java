package org.spaceinvaders.classes;

import java.awt.*;

/**
 * Helper class
 */
public class Helper {
    /**
     * Helper function to draw centered text
     * @param g2 Graphics2D object
     * @param text Text to draw
     * @param font Font used to draw text
     * @param x X position
     * @param y Y position
     */
    public static void drawStringCenter(Graphics2D g2, String text, Font font, int x, int y) {
        final FontMetrics metrics = g2.getFontMetrics(font);
        x -= metrics.stringWidth(text) / 2;
        y = y - metrics.getHeight() / 2 + metrics.getAscent();

        g2.setFont(font);
        g2.drawString(text, x, y);
    }
}
