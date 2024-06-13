package org.spaceinvaders.enemies;

import org.spaceinvaders.ai.AIPlayerPrediction;
import org.spaceinvaders.classes.Vector2D;
import org.spaceinvaders.entities.Enemy;
import org.spaceinvaders.main.GamePanel;

import java.awt.*;
import java.net.URL;

/**
 * Enemy with AI that predicts player position
 */
public class PredictorEnemy extends Enemy {
    public static final AIPlayerPrediction predictor = new AIPlayerPrediction();
    private static final double EPSILON = 10;
    private double nextPrediction = 0;

    /**
     * Constructs predictor enemy
     */
    public PredictorEnemy() {
        super();

        URL imgUrl = getClass().getClassLoader().getResource("images/enemy2.png");
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

        predictor.addEnemy(this);
    }
    @Override
    public void behaviour() {
        final Vector2D pos = getPosition();

        if(nextPrediction < System.currentTimeMillis()) {
            Double targetX = predictor.getResult(this);
            if(targetX != null && Math.abs(targetX - pos.getX()) <= EPSILON) {
                nextProjectile = 1;
                nextPrediction = System.currentTimeMillis() + rng.nextInt(1000, 2500);
            }
        }

        if(pos.getX() < 0) {
            setVelocity(new Vector2D(1, 0));
        } else if(pos.getX() > GamePanel.WIDTH) {
            setVelocity(new Vector2D(-1, 0));
        }
    }
}
