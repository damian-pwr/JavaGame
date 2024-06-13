package org.spaceinvaders.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Main menu class
 */
public class MainMenu extends JPanel {
    Image background;

    /**
     * Constructs main manu class. Loads background image and positions buttons
     */
    public MainMenu() {
        super();

        URL imgUrl = getClass().getClassLoader().getResource("images/menu.png");
        if(imgUrl != null) {
            try {
                background = ImageIO.read(imgUrl);
            } catch (IOException e) {
                System.err.println("Failed to load MainMenu background!");
            }
        }

        setBackground(Color.black);

        BoxLayout box = new BoxLayout(this,BoxLayout.PAGE_AXIS);
        setLayout(box);

        setBounds(0,0,1000,1000);
        JButton start = new JButton("Start");
        start.addActionListener(e -> SpaceInvaders.startGame());
        start.setPreferredSize(new Dimension(200,50));
        start.setMaximumSize(new Dimension(200,50));
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        start.setForeground(Color.BLACK);
        start.setBorderPainted(false);
        start.setFocusPainted(false);
        start.setContentAreaFilled(false);
        start.setOpaque(true);
        start.setFont(new Font("Arial", Font.PLAIN, 40));

        JLabel title = new JLabel();
        title.setText("Space Invaders");
        title.setForeground(Color.WHITE);
        title.setBackground(Color.BLACK);
        title.setOpaque(true);
        title.setFont(new Font("Geneva", Font.BOLD,70));
        title.setPreferredSize(new Dimension(100,300));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> SpaceInvaders.exit());
        exit.setPreferredSize(new Dimension(200,50));
        exit.setMaximumSize(new Dimension(200,50));
        exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        exit.setForeground(Color.BLACK);
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        exit.setContentAreaFilled(false);
        exit.setOpaque(true);
        exit.setFont(new Font("Arial", Font.PLAIN, 40));

        add(Box.createRigidArea(new Dimension(10,50)));
        add(title);
        add(Box.createRigidArea(new Dimension(10,550)));
        add(start);
        add(Box.createRigidArea(new Dimension(10,10)));
        add(exit);

    }

    /**
     * Draws background
     * @param g the <code>Graphics</code> object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(background != null) {
            g.drawImage(background, 0, 150, this);
        }
    }
}
