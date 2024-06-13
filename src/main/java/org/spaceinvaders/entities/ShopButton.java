package org.spaceinvaders.entities;

import org.spaceinvaders.classes.Entity;
import org.spaceinvaders.classes.Helper;
import org.spaceinvaders.classes.Rect;
import org.spaceinvaders.classes.Vector2D;
import org.spaceinvaders.interfaces.IShopButtonListener;
import org.spaceinvaders.main.GamePanel;

import java.awt.*;

/**
 * The ShopButton class
 * It extends the Entity class
 */
public class ShopButton extends Entity {
    private int price;
    private final int increase;
    private final String text;
    private final IShopButtonListener listener;

    private static final Font FONT_ARIAL20 = new Font("Arial", Font.PLAIN, 20);
    private static final Font FONT_ARIAL15 = new Font("Arial", Font.PLAIN, 15);

    /**
     * Constructs a ShopButton with the specified price, increase, text, and listener
     * @param price    The initial price of the item
     * @param increase The amount to increase the price by after each purchase
     * @param text     The text to display on the button
     * @param listener The listener to handle purchase events
     */
    public ShopButton(int price, int increase, String text, IShopButtonListener listener) {
        this.price = price;
        this.increase = increase;
        this.text = text;
        this.listener = listener;

        setMins(new Vector2D(-96, -48));
        setMaxs(new Vector2D(96, 48));
    }

    /**
     * Handles the collision event with another entity
     * If player has enough money, purchase item
     * @param other The other entity
     */
    @Override
    public void onCollision(Entity other) {
        if (other instanceof Projectile) {
            final Player ply = GamePanel.activeGame.getGameManager().getPlayer();

            if (ply.getMoney() < price) return;
            if (!listener.onPurchase(this)) return;

            ply.setMoney(ply.getMoney() - price);
            price += increase;
        }
    }

    /**
     * Renders the shop button on the screen
     * @param g2 The Graphics2D
     */
    @Override
    public void render(Graphics2D g2) {
        renderBounds(g2);

        final Rect bounds = getBounds();

        g2.setColor(Color.WHITE);
        Helper.drawStringCenter(g2, text, FONT_ARIAL20, (int) (bounds.getX() + bounds.getWidth() / 2), (int) (bounds.getY() + bounds.getHeight() / 3));

        if (price > 0) {
            Helper.drawStringCenter(g2, "Koszt: " + price, FONT_ARIAL15, (int) (bounds.getX() + bounds.getWidth() / 2), (int) (bounds.getY() + bounds.getHeight() * 2 / 3));
        }
    }

    /**
     * Returns the current price of the item
     * @return The current price of the item
     */
    public int getPrice() {
        return price;
    }

    /**
     * Returns the amount by which the price increases after each purchase
     * @return The price increase amount
     */
    public int getIncrease() {
        return increase;
    }

    /**
     * Returns the text displayed on the button
     * @return The button's text
     */
    public String getText() {
        return text;
    }
}
