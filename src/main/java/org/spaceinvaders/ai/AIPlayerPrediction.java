package org.spaceinvaders.ai;

import org.spaceinvaders.classes.Vector2D;
import org.spaceinvaders.entities.Enemy;
import org.spaceinvaders.entities.Player;
import org.spaceinvaders.handlers.GameManager;
import org.spaceinvaders.main.GamePanel;

import java.util.ArrayList;
import java.util.List;

/**
 * AI thread that calculates player's position in the future, predicting where player will be
 */
public class AIPlayerPrediction extends AIBase<Double> {
    /**
     * Calculates future position of player based on projectile speed and player speed
     */
    @Override
    public synchronized void calculate() {
        final GameManager manager = GamePanel.activeGame.getGameManager();
        if(manager == null) return;

        final Player ply = manager.getPlayer();
        final double playerSpeed = ply.getSpeed();
        final double playerVelocityX = ply.getVelocity().getX();
        final Vector2D playerPosition = ply.getPosition();

        final List<Enemy> toRemove = new ArrayList<>();

        for(Enemy e : enemies) {
            if(e.getScene() == null) {
                toRemove.add(e);
            }

            final int projectileSpeed = e.getProjectileSpeed();
            final double ourY = e.getPosition().getY();

            final double time = Math.abs(ourY - playerPosition.getY()) / projectileSpeed;
            final double predictedPlayerX = playerPosition.getX() + playerVelocityX * playerSpeed * time;

            result.put(e, predictedPlayerX);
        }

        for(Enemy e : toRemove) {
            enemies.remove(e);
            result.remove(e);
        }
    }
}
