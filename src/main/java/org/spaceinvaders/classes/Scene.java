package org.spaceinvaders.classes;

import org.spaceinvaders.interfaces.IGameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Game scene.<br>
 * Holds list of <code>IGameObject</code><br>
 * Used to quickly swap between scenes
 */
public class Scene {
    private final List<IGameObject> objects = new ArrayList<>();
    private final List<IGameObject> addObjects = new ArrayList<>();
    private final List<IGameObject> removeObjects = new ArrayList<>();

    /**
     * Adds object to the scene
     * @param object object to add
     */
    public void addGameObject(IGameObject object) {
        addObjects.add(object);
    }

    /**
     * Removes object from the scene
     * @param object object to remove
     */
    public void removeGameObject(IGameObject object) {
        removeObjects.add(object);
    }

    /**
     * Clear the scene
     * @param restart True if game was restarted
     */
    public void clear(boolean restart) {
        removeObjects.addAll(objects);
    }

    /**
     * Handles scene collision
     */
    public void handleCollisions() {
        for(int i = 0; i < objects.size() - 1; i++) {
            IGameObject obj1 = objects.get(i);
            if(!(obj1 instanceof Entity ent1)) continue;

            for(int j = i + 1; j < objects.size(); j++) {
                IGameObject obj2 = objects.get(j);
                if(!(obj2 instanceof Entity ent2)) continue;

                if(ent1.shouldCollide(ent2) && ent2.shouldCollide(ent1)) {
                    ent1.onCollision(ent2);
                    ent2.onCollision(ent1);
                }
            }
        }
    }

    /**
     * Returns list of scene objects
     * @return list of scene objects
     */
    public List<IGameObject> getObjects() {
        return objects;
    }

    /**
     * Do nothing when activated
     */
    public void onActivated() {

    }

    /**
     * Updates all game objects in the scene.<br>
     * Uses delta time to maintain same game speed on slower PCs or in case of FPS limit change
     * @param deltaTime time since last update in milliseconds.
     */
    public void update(double deltaTime) {
        for(IGameObject r : objects){
            r.update(deltaTime);
        }

        objects.removeAll(removeObjects);
        objects.addAll(addObjects);

        removeObjects.clear();
        addObjects.clear();
    }

    /**
     * Render the scene
     * @param g2 Graphics2D object
     */
    public void render(Graphics2D g2) {
        for(IGameObject r : objects){
            r.render(g2);
        }
    }
}
