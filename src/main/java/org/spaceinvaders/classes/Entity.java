package org.spaceinvaders.classes;

import org.spaceinvaders.interfaces.IGameObject;

import java.awt.*;
import java.net.URL;

/**
 * Entity class represents object in game.
 * Abstract class that provides basic functionality for game entities,
 * such as position, velocity, rendering, and collision detection
 */
public abstract class Entity implements IGameObject {
    private Scene scene;
    private Vector2D position, velocity, mins, maxs;
    private double speed = 0;
    private double destroyed = 0;
    private Image img;
    private Image explosion;

    /**
     * Constructs an Entity
     */
    public Entity() {
        position = new Vector2D();
        velocity = new Vector2D();
        mins = new Vector2D();
        maxs = new Vector2D();

        URL imgUrl = getClass().getClassLoader().getResource("images/explosion.gif");
        if(imgUrl != null) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            explosion = tk.getImage(imgUrl);
        }
    }

    /**
     * Updates entity. Takes <code>deltaTime</code> into account to allows smooth updates
     * @param deltaTime The time elapsed since the last update, in seconds
     */
    @Override
    public void update(double deltaTime) {
        if(destroyed > 0){
            if(destroyed <= System.currentTimeMillis()){
                setScene(null);
            }

            return;
        }

        final Vector2D newVelocity = velocity.copy();
        newVelocity.mul(speed * deltaTime);

        position.add(newVelocity);
    }

    /**
     * Renders the entity.
     * @param g2 Graphics2D object.
     */
    @Override
    public void render(Graphics2D g2) {
        if(renderExplosion(g2)) return;

        final Rect bounds = getBounds();

        g2.setColor(Color.WHITE);

        if(img == null) {
            g2.fillRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());
        } else {
            g2.drawImage(img, (int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight(), null);
        }

        //renderBounds(g2);
    }

    /**
     * Marks the entity as destroyed and removes it from the scene
     */
    public void destroy() {
        setScene(null);
    }

    /**
     * Starts explosion sequence for the entity
     */
    public void explode() {
        destroyed = System.currentTimeMillis() + 500;
    }

    /**
     * Sets the image used to render the entity
     * @param img image to set
     */
    public void setImg(Image img) {
        this.img = img;
    }

    /**
     * Gets the time at which the entity was destroyed
     * @return The time the entity was destroyed
     */
    public double getDestroyed() {
        return destroyed;
    }

    /**
     * Sets the time at which the entity was destroyed
     * @param d The time to set
     */
    public void setDestroyed(double d) {
        destroyed = d;
    }

    /**
     * Calculates the radius of the entity based on its bounds
     * @return radius of the entity
     */
    public double getRadius() {
        return Math.max(Math.abs(mins.getX()),
                Math.max(Math.abs(mins.getY()),
                Math.max(Math.abs(maxs.getX()), Math.abs(maxs.getY()))));
    }

    /**
     * Determines whether the entity should collide with another entity
     * @param other other entity to check collision with
     * @return True if the entities should collide
     */
    public boolean shouldCollide(Entity other) {
        if(destroyed != 0) return false;

        return getBounds().intersects(other.getBounds());
    }

    /**
     * Handles collision with other entity
     * @param other other entity
     */
    public abstract void onCollision(Entity other);

    /**
     * Gets the scene
     * @return The scene of the entity
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Renders the bounds of the entity for debugging
     * @param g2 The Graphics2D object
     */
    public void renderBounds(Graphics2D g2) {
        final Rect bounds = getBounds();

        g2.setColor(Color.WHITE);
        g2.drawRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());
    }

    /**
     * Renders the explosion animation of the entity
     * @param g2 The Graphics2D object
     * @return True if the explosion was rendered
     */
    public boolean renderExplosion(Graphics2D g2) {
        if(destroyed == 0) {
            return false;
        } else if(destroyed <= System.currentTimeMillis()) {
            return true;
        }

        final Rect bounds = getBounds();
        g2.drawImage(explosion, (int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight(), null);

        return true;
    }

    /**
     * Sets the scene of entity
     * @param scene scene
     */
    public void setScene(Scene scene) {
        if(this.scene != null) {
            this.scene.removeGameObject(this);
        }

        if(scene != null) {
            scene.addGameObject(this);
        }

        this.scene = scene;
    }

    /**
     * Gets the position of the entity
     * @return The position of the entity
     */
    public Vector2D getPosition() {
        return position;
    }

    /**
     * Sets the position of the entity
     * @param position The position to set
     */
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    /**
     * Gets the velocity of the entity
     * @return The velocity of the entity
     */
    public Vector2D getVelocity() {
        return velocity;
    }

    /**
     * Sets the velocity of the entity
     * @param velocity The velocity to set
     */
    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    /**
     * Gets the minimum bounds of the entity
     * @return The minimum bounds of the entity
     */
    public Vector2D getMins() {
        return mins;
    }

    /**
     * Sets the minimum bounds of the entity
     * @param mins The minimum bounds to set
     */
    public void setMins(Vector2D mins) {
        this.mins = mins;
    }

    /**
     * Gets the maximum bounds of the entity
     * @return The maximum bounds of the entity
     */
    public Vector2D getMaxs() {
        return maxs;
    }

    /**
     * Sets the maximum bounds of the entity
     * @param maxs The maximum bounds to set
     */
    public void setMaxs(Vector2D maxs) {
        this.maxs = maxs;
    }

    /**
     * Gets the speed of the entity
     * @return The speed of the entity
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets the speed of the entity
     * @param speed The speed to set
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Gets the bounding rectangle of the entity
     * @return The bounding box
     */
    public Rect getBounds() {
        final Vector2D v1 = position.copy();
        v1.add(mins);

        final Vector2D v2 = position.copy();
        v2.add(maxs);

        return new Rect(v1, v2);
    }
}
