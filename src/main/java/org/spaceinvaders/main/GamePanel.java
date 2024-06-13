package org.spaceinvaders.main;

import org.spaceinvaders.handlers.GameManager;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel that handles the actual game (game loop)
 */
public class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 1000;
    public static final Font FPS_FONT = new Font("Arial", Font.PLAIN, 10);
    public static GamePanel activeGame;
    private double fps = 0;
    private double dt = 0;
    private Thread gameThread;
    private GameManager gameManager;

    /**
     * Constructs a GamePanel<br>
     * Sets up basic properties of JPanel (size, background, and layout)
     * @param cmd boolean indicating whether command line key listener should be added
     */
    public GamePanel(boolean cmd) {
        // Panel settings
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setLayout(null);
        setFocusable(true);

        if (cmd) {
            this.addKeyListener(CommandLine.CMD);
        }

        activeGame = this;
    }

    /**
     * Starts the game thread and creates the game manager
     */
    public void startGame() {
        gameManager = new GameManager();
        addKeyListener(gameManager);

        gameThread = new Thread(this);
        gameThread.start();

        requestFocus();
    }

    /**
     * Gets the game manager
     * @return the GameManager instance
     */
    public GameManager getGameManager() {
        return gameManager;
    }

    /**
     * Draws all game objects
     * @param g the Graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (gameManager != null) {
            gameManager.render(g2);
        }

        g2.setColor(Color.WHITE);
        g2.setFont(FPS_FONT);
        g2.drawString("FPS: " + fps, 0, 10);
        g2.drawString("DT: " + dt + "ms", 0, 20);

        if (CommandLine.CMD.visible) {
            g2.setColor(Color.WHITE);
            g2.drawLine(0, HEIGHT - 15, WIDTH, HEIGHT - 15);
            g2.drawString("> " + CommandLine.CMD.command, 5, HEIGHT - 3);
        }

        g2.dispose();
    }

    /**
     * The actual game loop.<br>
     * Limits FPS to 60 to limit GPU usage.
     */
    @Override
    public void run() {
        // Helper variables
        final double FPS = 60;
        final double DRAW_INTERVAL = 1000000000d / FPS;

        double lastDraw = System.nanoTime();
        int lastDrawInt = (int) (lastDraw / 1000000000d);

        int frames = 0;

        System.out.println("Start main loop");

        while (gameThread != null) {
            double now = System.nanoTime();
            double delta = now - lastDraw;

            // Update and render game
            if (delta >= DRAW_INTERVAL) {
                lastDraw = now;
                dt = delta / 1000000;
                frames++;

                gameManager.update(delta / 1000000000);
                repaint();
            }

            // Update FPS counter
            int nowInt = (int) (now / 1000000000);
            if (nowInt > lastDrawInt) {
                fps = frames;
                frames = 0;
                lastDrawInt = nowInt;
            }

            // Yield to save tiny bit of CPU power
            Thread.yield();
        }
    }
}