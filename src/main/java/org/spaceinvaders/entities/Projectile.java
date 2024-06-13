package org.spaceinvaders.entities;

import org.spaceinvaders.classes.Entity;
import org.spaceinvaders.classes.Vector2D;
import org.spaceinvaders.main.GamePanel;

import java.awt.*;

/**
 * The Projectile class
 * It extends the Entity class
 */
public class Projectile extends Entity {
    private Color color;
    private boolean shotByPlayer = false;

    /**
     * Constructs a Projectile
     */
    public Projectile() {
        super();

        setVelocity(new Vector2D(0, -1));
        setMins(new Vector2D(-2, -8));
        setMaxs(new Vector2D(2, 8));
    }

    /**
     * Updates the projectile's state
     * If the projectile moves off the screen, it is destroyed
     * @param deltaTime The time elapsed since the last update, in seconds
     */
    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        if (getPosition().getY() < 0 || getPosition().getY() > GamePanel.HEIGHT) {
            destroy();
        }
    }

    /**
     * Determines whether the projectile should collide with another entity
     * Projectiles do not collide with entities of the same type that shot them (player or enemy)
     * @param other The other entity
     * @return True if the projectile should collide
     */
    @Override
    public boolean shouldCollide(Entity other) {
        if (shotByPlayer == other instanceof Player) {
            return false;
        }

        return super.shouldCollide(other);
    }

    /**
     * Handles the collision event with another entity
     * Upon collision, the projectile is destroyed
     * @param other The other entity
     */
    @Override
    public void onCollision(Entity other) {
        destroy();
    }

    /**
     * Sets the color of the projectile
     * @param color The color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets whether the projectile was shot by the player
     * @param shotByPlayer True if the projectile was shot by the player
     */
    public void setShotByPlayer(boolean shotByPlayer) {
        this.shotByPlayer = shotByPlayer;
    }
}
