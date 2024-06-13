package org.spaceinvaders.handlers;

import org.spaceinvaders.classes.Scene;
import org.spaceinvaders.classes.Vector2D;
import org.spaceinvaders.dto.WaveDTO;
import org.spaceinvaders.enemies.BasicEnemy;
import org.spaceinvaders.enemies.BelowEnemy;
import org.spaceinvaders.enemies.PredictorEnemy;
import org.spaceinvaders.enemies.ZigzagEnemy;
import org.spaceinvaders.entities.Enemy;
import org.spaceinvaders.main.GamePanel;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

/**
 * Wave manager class
 */
public class WaveManager {
    public static final WaveManager DEFAULT_MANAGER = new WaveManager();
    private static final Random rng = new Random();

    /**
     * Spawns enemy wave based on Wave DTO
     * @param dto WaveDTO
     * @param scene Scene to spawn enemies in
     */
    public void spawnWave(WaveDTO dto, Scene scene) {
        spawnZigzagEnemies(dto.zigzagEnemies, scene);

        try {
            spawnGenericEnemies(BasicEnemy.class, dto.basicEnemies, scene);
            spawnGenericEnemies(PredictorEnemy.class, dto.predictorEnemies, scene);
            spawnGenericEnemies(BelowEnemy.class, dto.belowEnemies, scene);
        } catch(Exception e) {
            System.err.println("Failed to spawn enemies!");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Generate WaveDTO based on wave number
     * @param wave Wave number
     * @return WaveDTO object
     */
    public WaveDTO generateWave(int wave) {
        final WaveDTO dto = new WaveDTO();

        if(wave <= 5) {
            dto.basicEnemies = 2 + wave * 2;
            dto.zigzagEnemies = wave - 1;
            dto.predictorEnemies = 0;
            dto.belowEnemies = 0;
        } else if(wave <= 10) {
            dto.basicEnemies = wave * 2 - 6;
            dto.zigzagEnemies = wave;
            dto.predictorEnemies = wave / 2 - 2;
            dto.belowEnemies = wave / 3 - 2;
        } else if(wave <= 15) {
            dto.basicEnemies = wave * 2 - 12;
            dto.zigzagEnemies = wave - 3;
            dto.predictorEnemies = wave / 2;
            dto.belowEnemies = wave / 3;
        } else {
            dto.basicEnemies = wave;
            dto.zigzagEnemies = wave / 2;
            dto.predictorEnemies = wave / 2 - 3;
            dto.belowEnemies = wave / 3 - 2;
        }

        return dto;
    }

    /**
     * Generic spawn function
     * @param c Class of enemy to spawn
     * @param num Number of enemies to spawn
     * @param scene Scene to spawn enemies in
     * @throws NoSuchMethodException NoSuchMethodException
     * @throws InvocationTargetException InvocationTargetException
     * @throws InstantiationException InstantiationException
     * @throws IllegalAccessException IllegalAccessException
     */
    private void spawnGenericEnemies(Class<?> c, int num, Scene scene)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for(int i = 0; i < num; i++) {
            Enemy basic = (Enemy) c.getConstructor().newInstance();
            basic.setScene(scene);

            Vector2D pos = new Vector2D(rng.nextInt(250, 750), rng.nextInt(64, GamePanel.HEIGHT / 3));

            if(rng.nextBoolean()) {
                pos.setX(-pos.getX());
            } else {
                pos.setX(pos.getX() + GamePanel.WIDTH);
            }

            basic.setPosition(pos);
        }
    }

    /**
     * Spawns zigzag enemies
     * @param num number of enemies
     * @param scene scene to add enemies to
     */
    private void spawnZigzagEnemies(int num, Scene scene) {
        for(int i = 0; i < num; i++) {
            Enemy zigzag = new ZigzagEnemy();
            zigzag.setScene(scene);

            Vector2D pos = new Vector2D(rng.nextInt(250, 750), rng.nextInt(GamePanel.HEIGHT / 4, GamePanel.HEIGHT / 3));

            if(rng.nextBoolean()) {
                pos.setX(-pos.getX());
            } else {
                pos.setX(pos.getX() + GamePanel.WIDTH);
            }

            zigzag.setPosition(pos);
        }
    }
}
