package org.spaceinvaders.entities;

import org.spaceinvaders.classes.Entity;
import org.spaceinvaders.classes.Helper;
import org.spaceinvaders.classes.Rect;
import org.spaceinvaders.classes.Vector2D;
import org.spaceinvaders.dto.CoinDTO;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Coin extends Entity {
    public static final CoinDTO COIN_1 = new CoinDTO(1, Color.yellow.darker());
    public static final CoinDTO COIN_3 = new CoinDTO(3, Color.cyan.darker());
    public static final CoinDTO COIN_5 = new CoinDTO(5, Color.green.darker());
    public static final CoinDTO COIN_10 = new CoinDTO(10, Color.blue.darker());
    public static final CoinDTO COIN_15 = new CoinDTO(15, Color.red.darker());
    public static final List<CoinDTO> COINS = Arrays.asList(COIN_1, COIN_3, COIN_5, COIN_10, COIN_15);
    private static final Font FONT_ARIAL20 = new Font("Arial", Font.PLAIN, 20);
    private final int value;
    private final Color color;

    /**
     * Constructs coin based on value and color
     * @param value Value of coin
     * @param color Color of coin
     */
    public Coin(int value, Color color) {
        this.value = value;
        this.color = color;

        setMins(new Vector2D(-12, -12));
        setMaxs(new Vector2D(12, 12));
        setVelocity(new Vector2D(0, 1));
        setSpeed(300 + value * 5);
    }

    /**
     * Constructs coin based on coin DTO
     * @param dto CoinDTO
     */
    public Coin(CoinDTO dto) {
        this(dto.value, dto.color);
    }

    /**
     * Collide only with players
     * @param other other entity to check collision with
     * @return
     */
    @Override
    public boolean shouldCollide(Entity other) {
        if(!(other instanceof Player)) return false;
        return super.shouldCollide(other);
    }

    /**
     * Remove coin on collision
     * @param other other entity
     */
    @Override
    public void onCollision(Entity other) {
        setScene(null);
    }

    /**
     * Render coin and its value
     * @param g2 Graphics2D object.
     */
    @Override
    public void render(Graphics2D g2) {
        final Rect bounds = getBounds();

        g2.setColor(color);
        g2.fillOval((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());
        g2.setColor(Color.WHITE);
        Helper.drawStringCenter(g2, "$", FONT_ARIAL20, (int) (bounds.getX() + bounds.getWidth() / 2), (int) (bounds.getY() + bounds.getHeight() / 2));
    }

    /**
     * Gets value of coin
     * @return value of coin
     */
    public int getValue() {
        return value;
    }
}
