package org.spaceinvaders.ai;

import org.spaceinvaders.entities.Enemy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Base AI class
 * @param <T> type of returned value
 */
public abstract class AIBase<T> extends Thread{
    /**
     * Calculation result
     */
    protected final HashMap<Enemy, T> result = new HashMap<>();
    /**
     * List of enemies
     */
    protected final List<Enemy> enemies = new ArrayList<>();

    /**
     * Entry point of thread
     */
    @Override
    public void run() {
        while(!isInterrupted()) {
            calculate();

            try {
                sleep(1);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    /**
     * Called to perform calculation
     */
    public abstract void calculate();

    /**
     * Adds new enemy to the list
     * @param enemy <code>Enemy</code> to insert
     */
    public synchronized void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    /**
     * Removes enemy from the list
     * @param enemy <code>Enemy</code> to remove
     */
    public synchronized void removeEnemy(Enemy enemy) {
        this.enemies.remove(enemy);
        this.result.remove(enemy);
    }

    /**
     * Clears list of enemies
     */
    public synchronized void clear() {
        this.enemies.clear();
        this.result.clear();
    }

    /**
     * Returns calculation result for specified enemy
     * @param e <code>Enemy</code>
     * @return Calculation result
     */
    public synchronized T getResult(Enemy e) {
        return result.get(e);
    }
}
