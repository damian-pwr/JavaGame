package org.spaceinvaders.entities;

import org.spaceinvaders.classes.Entity;
import org.spaceinvaders.classes.Vector2D;
import org.spaceinvaders.main.GamePanel;

import java.awt.*;
import java.util.Random;

/**
 * Enemy class that represents an enemy
 * Extends the Entity class
 */
public abstract class Enemy extends Entity {
    protected final Random rng = new Random();
    protected double nextProjectile = 0;
    private int projectileSpeed = 300;

    /**
     * Constructs an Enemy
     */
    public Enemy() {
        super();

        setMins(new Vector2D(-24, -24));
        setMaxs(new Vector2D(24, 24));
    }

    /**
     * Defines the behavior of the enemy. This method should be implemented by subclasses
     */
    public abstract void behaviour();

    /**
     * Shoots a projectile from the enemy's position.
     */
    public void shotProjectile() {
        Vector2D pos = getPosition().copy();
        pos.add(new Vector2D(0, getMaxs().getY() + 8));

        Projectile p = new Projectile();
        p.setPosition(pos);
        p.setVelocity(new Vector2D(0, 1));
        p.setSpeed(projectileSpeed);
        p.setScene(GamePanel.activeGame.getGameManager().getActiveScene());
        p.setColor(Color.WHITE);
        p.setShotByPlayer(false);
    }

    /**
     * Handles the collision event with another entity
     * If the other entity is a projectile, the enemy explodes, the player's score is incremented, and
     * there is a chance to spawn a coin
     * @param other The other entity
     */
    @Override
    public void onCollision(Entity other) {
        if(other instanceof Projectile) {
            explode();
            GamePanel.activeGame.getGameManager().getPlayer().addScore();
            GamePanel.activeGame.getGameManager().enemies--;

            if(rng.nextFloat() < 0.125){
                spawnCoin();
            }
        }
    }

    /**
     * Updates the enemy state and fires a projectile
     * @param deltaTime The time elapsed since the last update, in seconds
     */
    @Override
    public void update(double deltaTime) {
        if(nextProjectile > 0 && nextProjectile <= System.currentTimeMillis()) {
            nextProjectile = 0;
            shotProjectile();
        }

        behaviour();

        super.update(deltaTime);
    }

    /**
     * Spawns a coin at the enemy's position
     */
    public void spawnCoin() {
        Coin coin = new Coin(Coin.COINS.get(rng.nextInt(Coin.COINS.size())));

        coin.setPosition(getPosition().copy());
        coin.setScene(GamePanel.activeGame.getGameManager().getActiveScene());
    }

    /**
     * Gets the speed of the enemy's projectiles
     * @return The speed of the projectiles
     */
    public int getProjectileSpeed() {
        return projectileSpeed;
    }

    /**
     * Sets the speed of the enemy's projectiles
     * @param projectileSpeed The speed to set
     */
    public void setProjectileSpeed(int projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }
}
