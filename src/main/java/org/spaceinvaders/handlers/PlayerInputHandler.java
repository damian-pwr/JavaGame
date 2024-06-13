package org.spaceinvaders.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The PlayerInputHandler class that implements KeyListener
 */
public class PlayerInputHandler implements KeyListener {
    public boolean left, right, space;

    /**
     * Invoked when a key has been typed. This event occurs when a key press is followed by a key release
     * @param e the KeyEvent associated with the key typed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // No implementation needed
    }

    /**
     * Invoked when a key has been pressed
     * Updates variables for left, right, and space keys based on the key code
     * @param e the KeyEvent associated with the key press
     */
    @Override
    public void keyPressed(KeyEvent e) {
        final int code = e.getKeyCode();

        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            left = true;
        }

        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            right = true;
        }

        if (code == KeyEvent.VK_SPACE) {
            space = true;
        }
    }

    /**
     * Invoked when a key has been released
     * Updates the variables for left, right, and space keys based on the key code
     * @param e the KeyEvent associated with the key release
     */
    @Override
    public void keyReleased(KeyEvent e) {
        final int code = e.getKeyCode();

        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            left = false;
        }

        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            right = false;
        }

        if (code == KeyEvent.VK_SPACE) {
            space = false;
        }
    }
}
