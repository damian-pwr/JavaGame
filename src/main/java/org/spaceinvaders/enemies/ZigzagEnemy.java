package org.spaceinvaders.enemies;

import org.spaceinvaders.classes.Vector2D;
import org.spaceinvaders.entities.Enemy;
import org.spaceinvaders.main.GamePanel;

import java.awt.*;
import java.net.URL;

/**
 * Simple enemy, no AI, moves in zigzag pattern
 */
public class ZigzagEnemy extends Enemy {
    private double changeDir = 0;

    /**
     * Constructs new zigzag enemy
     */
    public ZigzagEnemy() {
        super();

        URL imgUrl = getClass().getClassLoader().getResource("images/enemy1.png");
        if(imgUrl != null) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            setImg(tk.getImage(imgUrl));
        }

        Vector2D direction = new Vector2D(1, 1);

        if(rng.nextBoolean()) {
            direction.mul(-1);
        }

        setVelocity(direction);
        setSpeed(rng.nextInt(150, 200));
    }

    @Override
    public void behaviour() {
        if(nextProjectile == 0) {
            nextProjectile = System.currentTimeMillis() + rng.nextInt(2000, 5000);
        }

        final Vector2D pos = getPosition();
        if(pos.getX() < 0) {
            getVelocity().setX(1);
        } else if(pos.getX() > GamePanel.WIDTH) {
            getVelocity().setX(-1);
        }

        if(changeDir <= System.currentTimeMillis()) {
            changeDir = System.currentTimeMillis() + 500;

            final Vector2D vel = getVelocity();
            vel.setY(-vel.getY());
        }
    }
}
