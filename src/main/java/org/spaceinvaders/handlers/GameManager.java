package org.spaceinvaders.handlers;

import org.spaceinvaders.classes.Helper;
import org.spaceinvaders.classes.Scene;
import org.spaceinvaders.classes.Shop;
import org.spaceinvaders.dto.WaveDTO;
import org.spaceinvaders.enemies.BelowEnemy;
import org.spaceinvaders.enemies.PredictorEnemy;
import org.spaceinvaders.entities.Player;
import org.spaceinvaders.main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The GameManager class
 * It implements the KeyListener interface
 */
public class GameManager implements KeyListener {
    private static final Scene gameScene = new Scene();
    private static final Scene shopScene = new Shop();
    private static final Font FONT_ARIAL30 = new Font("Arial", Font.PLAIN, 30);
    private static final Font FONT_ARIAL50 = new Font("Arial", Font.PLAIN, 50);
    private final Player player;
    private Scene activeScene;
    private boolean paused = false;
    private boolean gameOver = false;
    private boolean shopVisited = false;
    private int wave = 0;
    private double drawWave = 0;
    private double enterShop = 0;
    public int enemies = 0;

    /**
     * Constructs a GameManager instance
     */
    public GameManager() {
        activeScene = gameScene;

        player = new Player();
        player.setScene(gameScene);
        GamePanel.activeGame.addKeyListener(player.getInputHandler());

        PredictorEnemy.predictor.start();
        BelowEnemy.below.start();
    }

    /**
     * Restarts the game by resetting the player, clearing scenes, and restarting game state
     */
    public void restartGame() {
        player.setDestroyed(0);
        player.resetStats();

        gameScene.clear(true);
        shopScene.clear(true);
        gameScene.update(0);
        shopScene.update(0);
        gameScene.clear(true);
        shopScene.clear(true);
        setActiveScene(gameScene);

        paused = false;
        gameOver = false;
        shopVisited = false;
        wave = 0;
        drawWave = 0;
        enterShop = 0;
        enemies = 0;
    }

    /**
     * Exits the shop and returns to the game scene
     */
    public void exitShop() {
        if (activeScene == shopScene) {
            shopScene.clear(false);
            setActiveScene(gameScene);
        }
    }

    /**
     * Updates the game state, handles wave progression, and scene transitions
     * @param deltaTime The time elapsed since the last update
     */
    public void update(double deltaTime) {
        if (paused || gameOver) return;

        if (enterShop == 0) {
            if (activeScene == gameScene && enemies <= 0) {
                if (!shopVisited && wave > 0 && wave % 5 == 0) {
                    enterShop = System.currentTimeMillis() + 5000;
                    return;
                }

                shopVisited = false;
                wave++;

                WaveDTO dto = WaveManager.DEFAULT_MANAGER.generateWave(wave);
                enemies = dto.total();

                WaveManager.DEFAULT_MANAGER.spawnWave(dto, gameScene);
                drawWave = System.currentTimeMillis() + 2000;
            }
        } else if (enterShop <= System.currentTimeMillis()) {
            shopVisited = true;
            enterShop = 0;
            gameScene.clear(false);
            setActiveScene(shopScene);
        }

        activeScene.update(deltaTime);
        activeScene.handleCollisions();

        if (player.getDestroyed() != 0) {
            gameOver = true;
        }
    }

    /**
     * Renders the current scene, player score and currency
     * @param g2 The Graphics2D
     */
    public void render(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(FONT_ARIAL30);
        g2.drawString("Wynik: " + player.getScore(), 5, GamePanel.HEIGHT - 70);
        g2.drawString("Waluta: " + player.getMoney(), 5, GamePanel.HEIGHT - 30);

        activeScene.render(g2);

        if (drawWave >= System.currentTimeMillis()) {
            Helper.drawStringCenter(g2, "Fala " + wave, FONT_ARIAL50, GamePanel.WIDTH / 2, GamePanel.HEIGHT / 3);
        }

        if (paused) {
            g2.setColor(Color.GRAY);
            g2.fillRect(0, GamePanel.HEIGHT / 3 - 50, GamePanel.WIDTH, 155);

            g2.setColor(Color.WHITE);
            Helper.drawStringCenter(g2, "Pauza", FONT_ARIAL50, GamePanel.WIDTH / 2, GamePanel.HEIGHT / 3);
            Helper.drawStringCenter(g2, "Naciśnij escape aby wznowić", FONT_ARIAL30, GamePanel.WIDTH / 2, GamePanel.HEIGHT / 3 + 50);
        } else if (gameOver) {
            g2.setColor(Color.RED.darker().darker());
            g2.fillRect(0, GamePanel.HEIGHT / 3 - 50, GamePanel.WIDTH, 280);

            g2.setColor(Color.WHITE);
            Helper.drawStringCenter(g2, "Game Over!", FONT_ARIAL50, GamePanel.WIDTH / 2, GamePanel.HEIGHT / 3);
            Helper.drawStringCenter(g2, "Wynik: " + player.getScore(), FONT_ARIAL50, GamePanel.WIDTH / 2, GamePanel.HEIGHT / 3 + 80);
            Helper.drawStringCenter(g2, "Najlepszy wynik: " + player.getHighScore(), FONT_ARIAL30, GamePanel.WIDTH / 2, GamePanel.HEIGHT / 3 + 120);
            Helper.drawStringCenter(g2, "Naciśnij spację aby zrestartować", FONT_ARIAL30, GamePanel.WIDTH / 2, GamePanel.HEIGHT / 3 + 180);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No implementation needed
    }

    @Override
    public void keyPressed(KeyEvent e) {
        final int code = e.getKeyCode();

        if (!gameOver && code == KeyEvent.VK_ESCAPE) {
            paused = !paused;
        } else if (gameOver && code == KeyEvent.VK_SPACE) {
            restartGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No implementation needed
    }

    /**
     * Sets the active scene and updates the player scene.
     * @param scene The new active scene
     */
    public void setActiveScene(Scene scene) {
        activeScene = scene;
        player.setScene(scene);
        scene.onActivated();
    }

    /**
     * Returns the active scene
     * @return The current active scene
     */
    public Scene getActiveScene() {
        return activeScene;
    }

    /**
     * Sets the current wave number
     * @param wave The new wave number
     */
    public void setWave(int wave) {
        this.wave = wave;
    }

    /**
     * Returns the player instance
     * @return The player instance
     */
    public Player getPlayer() {
        return player;
    }
}
