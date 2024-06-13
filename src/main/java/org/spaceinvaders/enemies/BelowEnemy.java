package org.spaceinvaders.enemies;

import org.spaceinvaders.ai.AIPlayerBelow;
import org.spaceinvaders.classes.Vector2D;
import org.spaceinvaders.entities.Enemy;
import org.spaceinvaders.main.GamePanel;

import java.awt.*;
import java.net.URL;

/**
 * Enemy with AI that shoots only when above player
 */
public class BelowEnemy extends Enemy {
    public static final AIPlayerBelow below = new AIPlayerBelow();
    private static final double EPSILON = 10;
    private double nextBelow = 0;
    private double dBShot = 0;
    private double iter = 0;

    /**
     * Constructs the enemy object
     */
    public BelowEnemy() {
        super();

        URL imgUrl = getClass().getClassLoader().getResource("images/enemy3.png");
        if(imgUrl != null) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            setImg(tk.getImage(imgUrl));
        }

        Vector2D direction = new Vector2D(1, 0);

        if(rng.nextBoolean()) {
            direction.mul(-1);
        }

        setVelocity(direction);
        setSpeed(100);

        below.addEnemy(this);
    }

    @Override
    public void behaviour() {
        final Vector2D pos = getPosition();
        if(nextBelow < System.currentTimeMillis()) {
            Double targetX = below.getResult(this);
            if(targetX != null && Math.abs(targetX - pos.getX()) <= EPSILON) {
                nextProjectile = 1;
                nextBelow = System.currentTimeMillis() + rng.nextInt(1500, 3000);
                dBShot = System.currentTimeMillis() + 100;
                iter = 2;
            }
        } else if (dBShot < System.currentTimeMillis() && iter > 0 ){
            iter = iter - 1;
            nextProjectile = 1;
            dBShot = System.currentTimeMillis() + 100;
        }

        if(pos.getX() < 0) {
            setVelocity(new Vector2D(1, 0));
        } else if(pos.getX() > GamePanel.WIDTH) {
            setVelocity(new Vector2D(-1, 0));
        }
    }
}
