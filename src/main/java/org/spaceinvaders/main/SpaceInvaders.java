package org.spaceinvaders.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * Main class of program<br>
 * Creates and configures main window
 */
public class SpaceInvaders {
    private static JFrame window;
    private static JPanel cards;
    private static GamePanel gp;

    /**
     * Entry point of program
     * @param args program arguments
     */
    public static void main(String[] args) {
        window = new JFrame("Space Invaders");

        //Basic window config
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        //Cards
        cards = new JPanel(new CardLayout());

        //Main Menu
        MainMenu main = new MainMenu();
        cards.add(main, "Menu");

        //GamePanel
        gp = new GamePanel(args.length > 0 && args[0].equals("-cmd"));
        cards.add(gp, "Game");

        //Pack window
        window.add(cards);
        window.pack();

        //Center window and show it
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    /**
     * Starts a game
     */
    public static void startGame() {
        //Switch cards to Game
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, "Game");

        //Start game loop
        gp.startGame();
    }

    /**
     * Exits application
     */
    public static void exit() {
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }
}