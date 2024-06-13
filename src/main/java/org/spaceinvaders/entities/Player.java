package org.spaceinvaders.entities;

import org.spaceinvaders.classes.Entity;
import org.spaceinvaders.classes.Rect;
import org.spaceinvaders.classes.Vector2D;
import org.spaceinvaders.handlers.PlayerInputHandler;
import org.spaceinvaders.main.GamePanel;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;

/**
 * The Player class
 * It extends the Entity class
 */
public class Player extends Entity {
    public static final int DEFAULT_FLY_SPEED = 180;
    public static final int DEFAULT_SHOT_INTERVAL = 800;
    public static final int DEFAULT_PROJECTILE_SPEED = 275;
    private final PlayerInputHandler inputHandler;

    private double nextProjectile = 0;
    private int score = 0;
    private int highScore = 0;
    private int money = 0;
    private int flySpeed = DEFAULT_FLY_SPEED;
    private int shotInterval = DEFAULT_SHOT_INTERVAL;
    private int projectileSpeed = DEFAULT_PROJECTILE_SPEED;
    private Image shieldImg;
    private boolean shield = false;

    /**
     * Constructs a Player
     */
    public Player() {
        super();

        URL imgUrl = getClass().getClassLoader().getResource("images/ship.gif");
        if (imgUrl != null) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            setImg(tk.getImage(imgUrl));
        }

        URL shieldUrl = getClass().getClassLoader().getResource("images/shield.png");
        if (shieldUrl != null) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            shieldImg = tk.getImage(shieldUrl);
        }

        inputHandler = new PlayerInputHandler();

        setPosition(new Vector2D(GamePanel.WIDTH / 2d, GamePanel.HEIGHT - 48));
        setVelocity(new Vector2D(1, 0));
        setMins(new Vector2D(-32, -32));
        setMaxs(new Vector2D(32, 32));

        loadHighScore();
    }

    /**
     * Fires a projectile from the player's position
     */
    public void fireProjectile() {
        nextProjectile = System.currentTimeMillis() + shotInterval;

        Vector2D pos = getPosition().copy();
        pos.add(new Vector2D(0, getMins().getY() - 8));

        Projectile p = new Projectile();
        p.setPosition(pos);
        p.setSpeed(projectileSpeed);
        p.setScene(GamePanel.activeGame.getGameManager().getActiveScene());
        p.setColor(Color.WHITE);
        p.setShotByPlayer(true);
    }

    /**
     * Resets the player's stats to their default values
     */
    public void resetStats() {
        score = 0;
        money = 0;
        flySpeed = DEFAULT_FLY_SPEED;
        shotInterval = DEFAULT_SHOT_INTERVAL;
        projectileSpeed = DEFAULT_PROJECTILE_SPEED;
        shield = false;
    }

    /**
     * Loads the high score from a file
     */
    public void loadHighScore() {
        try {
            File file = new File("score.dat");
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                highScore = scanner.nextInt();
            }
        } catch (IOException e) {
            System.err.println("Cannot read score.dat!");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Saves the high score to a file
     */
    public void saveHighScore() {
        try {
            PrintWriter writer = new PrintWriter("score.dat");
            writer.print(highScore);
            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot write to score.dat!");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Handles the collision event with another entity
     * If the other entity is a coin, the player's money is increased
     * If the player has a shield, the shield is deactivated upon collision
     * If the player does not have a shield, the player's ship explodes
     * @param other The other entity
     */
    @Override
    public void onCollision(Entity other) {
        if (other instanceof Coin coin) {
            money += coin.getValue();
            return;
        }

        if (shield) {
            shield = false;
            return;
        }

        if (score > highScore) {
            highScore = score;
            saveHighScore();
        }

        explode();
    }

    /**
     * Updates the player's state
     * This includes handling movement and firing projectiles
     * @param deltaTime The time elapsed since the last update, in seconds.
     */
    @Override
    public void update(double deltaTime) {
        if (getDestroyed() != 0) return;

        if (inputHandler.left && inputHandler.right || !inputHandler.left && !inputHandler.right) {
            setSpeed(0);
        } else if (inputHandler.left) {
            final Vector2D pos = getPosition();
            if (pos.getX() + getMins().getX() <= 0) {
                pos.setX(-getMins().getX());
                setSpeed(0);
            } else {
                setSpeed(-flySpeed);
            }
        } else {
            final Vector2D pos = getPosition();
            if (pos.getX() + getMaxs().getX() >= GamePanel.WIDTH) {
                pos.setX(GamePanel.WIDTH - getMaxs().getX());
                setSpeed(0);
            } else {
                setSpeed(flySpeed);
            }
        }

        if (inputHandler.space && nextProjectile <= System.currentTimeMillis()) {
            fireProjectile();
        }

        super.update(deltaTime);
    }

    /**
     * Renders the player and the shield if it is active
     * @param g2 The Graphics2D object used for drawing
     */
    @Override
    public void render(Graphics2D g2) {
        super.render(g2);

        if (shield && shieldImg != null) {
            final Rect bounds = getBounds();
            g2.drawImage(shieldImg, (int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight(), null);
        }
    }

    /**
     * Gets the player's current score
     * @return The player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the player's score
     * @param score The score to set
     */
    @SuppressWarnings("unused")
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Increments the player's score by one
     */
    public void addScore() {
        this.score += 1;
    }

    /**
     * Gets the player's high score
     * @return The player's high score
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Sets the player's high score
     * @param highScore The high score to set
     */
    @SuppressWarnings("unused")
    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    /**
     * Gets the player's current money
     * @return The player's money
     */
    public int getMoney() {
        return money;
    }

    /**
     * Sets the player's money
     * @param money The money to set
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Gets the player's flying speed
     * @return The player's flying speed
     */
    public int getFlySpeed() {
        return flySpeed;
    }

    /**
     * Sets the player's flying speed
     * @param flySpeed The flying speed to set
     */
    public void setFlySpeed(int flySpeed) {
        this.flySpeed = flySpeed;
    }

    /**
     * Gets the interval between the player's shots
     * @return The interval between shots, in milliseconds
     */
    public int getShotInterval() {
        return shotInterval;
    }

    /**
     * Sets the interval between the player's shots
     * @param shotInterval The shot interval to set, in milliseconds
     */
    public void setShotInterval(int shotInterval) {
        this.shotInterval = shotInterval;
    }

    /**
     * Gets the speed of the player's projectiles
     * @return The projectile speed
     */
    public int getProjectileSpeed() {
        return projectileSpeed;
    }

    /**
     * Sets the speed of the player's projectiles
     * @param projectileSpeed The projectile speed to set
     */
    public void setProjectileSpeed(int projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    /**
     * Checks if the shield is active
     * @return True if the shield is active, false otherwise
     */
    public boolean isShield() {
        return shield;
    }

    /**
     * Sets the shield status
     * @param shield True to activate the shield, false to deactivate it
     */
    public void setShield(boolean shield) {
        this.shield = shield;
    }

    /**
     * Gets the player's input handler
     * @return The PlayerInputHandler object
     */
    public PlayerInputHandler getInputHandler() {
        return inputHandler;
    }
}
