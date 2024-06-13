package org.spaceinvaders.classes;

import org.spaceinvaders.entities.Player;
import org.spaceinvaders.entities.ShopButton;
import org.spaceinvaders.main.GamePanel;

import java.awt.*;

/**
 * Shop scene
 */
public class Shop extends Scene{
    private static final Font FONT_ARIAL30 = new Font("Arial", Font.PLAIN, 30);
    private static final Font FONT_ARIAL20 = new Font("Arial", Font.PLAIN, 20);

    public static final int DEFAULT_PLAYER_SPEED_COST = 10;
    public static final int DEFAULT_SHOT_TIME_COST = 5;
    public static final int DEFAULT_PROJECTILE_SPEED_COST = 15;
    public static final int DEFAULT_SHIELD_COST = 10;

    private int playerSpeedCost = DEFAULT_PLAYER_SPEED_COST;
    private int shotTimeCost = DEFAULT_SHOT_TIME_COST;
    private int projectileSpeedCost = DEFAULT_PROJECTILE_SPEED_COST;
    private int shieldCost = DEFAULT_SHIELD_COST;

    @Override
    public void clear(boolean restart) {
        super.clear(restart);

        if(restart) {
            playerSpeedCost = DEFAULT_PLAYER_SPEED_COST;
            shotTimeCost = DEFAULT_SHOT_TIME_COST;
            projectileSpeedCost = DEFAULT_PROJECTILE_SPEED_COST;
            shieldCost = DEFAULT_SHIELD_COST;
        }
    }

    /**
     * Spawn shop buttons when activated
     */
    @Override
    public void onActivated() {
        final Player ply = GamePanel.activeGame.getGameManager().getPlayer();
        double position = GamePanel.WIDTH / 6d;

        ShopButton exit = new ShopButton(0, 0, "Wyjdź ze sklepu", btn -> {
            GamePanel.activeGame.getGameManager().exitShop();
            return true;
        });

        exit.setPosition(new Vector2D(position * 3, GamePanel.HEIGHT / 2d));
        exit.setScene(this);

        if(ply.getFlySpeed() < 360) {
            ShopButton playerSpeed = new ShopButton(playerSpeedCost, 5, "Prędkość gracza", btn -> {
                ply.setFlySpeed(ply.getFlySpeed() + 25);
                playerSpeedCost = btn.getPrice() + btn.getIncrease();
                //System.out.println("New speed " + ply.getFlySpeed());

                if (ply.getFlySpeed() >= 360) {
                    btn.destroy();
                }

                return true;
            });

            playerSpeed.setPosition(new Vector2D(position * 1, GamePanel.HEIGHT / 2d));
            playerSpeed.setScene(this);
        }

        if(ply.getShotInterval() > 400) {
            ShopButton shotTime = new ShopButton(shotTimeCost, 5, "Szybkostrzelność", btn -> {
                ply.setShotInterval(ply.getShotInterval() - 60);
                shotTimeCost = btn.getPrice() + btn.getIncrease();
                //System.out.println("New interval " + ply.getShotInterval());

                if (ply.getShotInterval() <= 400) {
                    btn.destroy();
                }

                return true;
            });

            shotTime.setPosition(new Vector2D(position * 2, GamePanel.HEIGHT / 2d));
            shotTime.setScene(this);
        }

        if(ply.getProjectileSpeed() < 475) {
            ShopButton projectileSpeed = new ShopButton(projectileSpeedCost, 5, "Szybkość pocisków", btn -> {
                ply.setProjectileSpeed(ply.getProjectileSpeed() + 25);
                projectileSpeedCost = btn.getPrice() + btn.getIncrease();
                //System.out.println("New projectile speed " + ply.getProjectileSpeed());

                if (ply.getProjectileSpeed() >= 475) {
                    btn.destroy();
                }

                return true;
            });

            projectileSpeed.setPosition(new Vector2D(position * 4, GamePanel.HEIGHT / 2d));
            projectileSpeed.setScene(this);
        }

        if(!ply.isShield()) {
            ShopButton shield = new ShopButton(shieldCost, 10, "Tarcza", btn -> {
                if (ply.isShield()) return false;

                ply.setShield(true);
                shieldCost = btn.getPrice() + btn.getIncrease();
                btn.destroy();
                //System.out.println("Shield active");
                return true;
            });

            shield.setPosition(new Vector2D(position * 5, GamePanel.HEIGHT / 2d));
            shield.setScene(this);
        }
    }

    @Override
    public void render(Graphics2D g2) {
        super.render(g2);

        g2.setColor(Color.WHITE);
        Helper.drawStringCenter(g2, "Sklep", FONT_ARIAL30, GamePanel.WIDTH / 2, GamePanel.HEIGHT / 4);
        Helper.drawStringCenter(g2, "Strzel w blok aby zakupić ulepszenie lub wyjść ze sklepu", FONT_ARIAL20, GamePanel.WIDTH / 2, GamePanel.HEIGHT / 4 + 50);
    }
}
