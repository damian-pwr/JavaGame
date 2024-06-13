package org.spaceinvaders.interfaces;

import java.awt.*;

/**
 * Interface that represents game object
 */
public interface IGameObject {
    /**
     * Update function
     * Place to handle movement, collision, etc.
     * @param deltaTime - time elapsed since last render in milliseconds
     */
    public void update(double deltaTime);

    /**
     * Render function
     * Place to draw object
     * @param g2 <code>Graphics2D</code>
     */
    public void render(Graphics2D g2);
}
