package org.spaceinvaders.ai;

import org.spaceinvaders.classes.Vector2D;
import org.spaceinvaders.entities.Enemy;
import org.spaceinvaders.entities.Player;
import org.spaceinvaders.handlers.GameManager;
import org.spaceinvaders.main.GamePanel;

import java.util.ArrayList;
import java.util.List;

/**
 * AI thread that calculates ideal position for enemy based on current player position
 */
public class AIPlayerBelow extends AIBase<Double> {
    /**
     * Performs checks and calculations<br>
     * Sets current player location as result
     */
    @Override
    public synchronized void calculate() {
        final GameManager manager = GamePanel.activeGame.getGameManager();
        if(manager == null) return;

        final Player ply = manager.getPlayer();
        final Vector2D playerPosition = ply.getPosition();

        final List<Enemy> toRemove = new ArrayList<>();

        for(Enemy e : enemies) {
            if(e.getScene() == null) {
                toRemove.add(e);
            }

            final double currentPlayerPos = playerPosition.getX();
            result.put(e, currentPlayerPos);
        }

        for(Enemy e : toRemove) {
            enemies.remove(e);
            result.remove(e);
        }
    }
}
