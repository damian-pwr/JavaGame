package org.spaceinvaders.enemies;

import org.spaceinvaders.classes.Vector2D;
import org.spaceinvaders.entities.Enemy;
import org.spaceinvaders.main.GamePanel;

import java.awt.*;
import java.net.URL;

/**
 * Basic enemy, no AI
 */
public class BasicEnemy extends Enemy {
    /**
     * Constructs new basic enemy
     */
    public BasicEnemy() {
        super();

        URL imgUrl = getClass().getClassLoader().getResource("images/enemy1.png");
        if(imgUrl != null) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            setImg(tk.getImage(imgUrl));
        }

        Vector2D direction = new Vector2D(1, 0);

        if(rng.nextBoolean()) {
            direction.mul(-1);
        }

        setVelocity(direction);
        setSpeed(rng.nextInt(100, 200));
    }

    @Override
    public void behaviour() {
        if(nextProjectile == 0) {
            nextProjectile = System.currentTimeMillis() + rng.nextInt(2000, 4000);
        }

        final Vector2D pos = getPosition();
        if(pos.getX() < 0) {
            setVelocity(new Vector2D(1, 0));
        } else if(pos.getX() > GamePanel.WIDTH) {
            setVelocity(new Vector2D(-1, 0));
        }
    }
}
