package org.spaceinvaders.dto;

/**
 * Wave data transfer object
 */
public class WaveDTO {
    public int basicEnemies;
    public int zigzagEnemies;
    public int predictorEnemies;
    public int belowEnemies;

    public int total() {
        return basicEnemies + zigzagEnemies + predictorEnemies + belowEnemies;
    }
}
