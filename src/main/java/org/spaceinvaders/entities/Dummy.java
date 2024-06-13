package org.spaceinvaders.entities;

import org.spaceinvaders.classes.Entity;
import org.spaceinvaders.classes.Vector2D;

/**
 * Dummy enemy used for testing
 */
public class Dummy extends Entity {

    public Dummy(double x, double y, double w, double h) {
        super();

        setPosition(new Vector2D(x + w / 2, y + h / 2));
        setMins(new Vector2D(-w / 2, -h / 2));
        setMaxs(new Vector2D(w / 2, h / 2));
    }

    @Override
    public void onCollision(Entity other) {
        explode();
    }
}
