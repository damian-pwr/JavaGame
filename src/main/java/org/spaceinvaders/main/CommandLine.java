package org.spaceinvaders.main;

import org.spaceinvaders.classes.Entity;
import org.spaceinvaders.classes.Vector2D;
import org.spaceinvaders.enemies.BasicEnemy;
import org.spaceinvaders.enemies.BelowEnemy;
import org.spaceinvaders.enemies.PredictorEnemy;
import org.spaceinvaders.enemies.ZigzagEnemy;
import org.spaceinvaders.entities.Dummy;
import org.spaceinvaders.entities.Enemy;
import org.spaceinvaders.entities.Projectile;
import org.spaceinvaders.handlers.WaveManager;
import org.spaceinvaders.interfaces.IGameObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Command line for development purposes. To enable it run program with -cmd argument
 */
public class CommandLine implements KeyListener {
    public boolean visible;
    public String command = "";

    public static final CommandLine CMD = new CommandLine();

    public CommandLine() {

    }

    /**
     * Handles command typed by user
     */
    public void handleCommand() {
        if(command.isBlank()) return;

        System.out.println("Executing command: " + command);

        String[] args = command.split(" ");

        switch (args[0]) {
            case "dummy" -> {
                System.out.printf("Spawning dummy at x=%s, y=%s", args[1], args[2]);
                Dummy d = new Dummy(Integer.parseInt(args[1]), Integer.parseInt(args[2]), 64, 64);
                d.setScene(GamePanel.activeGame.getGameManager().getActiveScene());
            }
            case "kill" -> {
                System.out.println("Killing player");

                Vector2D pos = GamePanel.activeGame.getGameManager().getPlayer().getPosition().copy();
                pos.add(new Vector2D(0, -300));

                Projectile p = new Projectile();
                p.setPosition(pos);
                p.setSpeed(300);
                p.setVelocity(new Vector2D(0, 1));
                p.setScene(GamePanel.activeGame.getGameManager().getActiveScene());
                p.setColor(Color.WHITE);
                p.setShotByPlayer(false);
            }
            case "basic" -> {
                System.out.println("Basic enemy");

                Vector2D pos = new Vector2D(Integer.parseInt(args[1]), Integer.parseInt(args[2]));

                Entity p = new BasicEnemy();
                p.setPosition(pos);
                p.setScene(GamePanel.activeGame.getGameManager().getActiveScene());
            }
            case "zigzag" -> {
                System.out.println("Zigzag enemy");

                Vector2D pos = new Vector2D(Integer.parseInt(args[1]), Integer.parseInt(args[2]));

                Entity p = new ZigzagEnemy();
                p.setPosition(pos);
                p.setScene(GamePanel.activeGame.getGameManager().getActiveScene());
            }
            case "wave" -> {
                int i = Integer.parseInt(args[1]);
                System.out.println("Spawning wave i=" + i);

                WaveManager.DEFAULT_MANAGER.spawnWave(WaveManager.DEFAULT_MANAGER.generateWave(i), GamePanel.activeGame.getGameManager().getActiveScene());
            }
            case "setwave" -> {
                int i = Integer.parseInt(args[1]);
                System.out.println("Setting wave i=" + i);

                GamePanel.activeGame.getGameManager().setWave(i);
            }
            case "clear" -> {
                System.out.println("Clearing!");

                for (IGameObject o : GamePanel.activeGame.getGameManager().getActiveScene().getObjects()) {
                    if (o instanceof Enemy enemy) {
                        enemy.explode();
                    }
                }

                GamePanel.activeGame.getGameManager().enemies = 0;
            }
            case "money" -> {
                int i = Integer.parseInt(args[1]);
                System.out.println("Set money to " + i);

                GamePanel.activeGame.getGameManager().getPlayer().setMoney(i);
            }
            case "shop" -> {
                command = "setwave 5";
                handleCommand();
                command = "clear";
                handleCommand();
            }
            case "predictor" -> {
                System.out.println("Spawning predictor!");
                PredictorEnemy pe = new PredictorEnemy();
                pe.setPosition(new Vector2D(500, 300));
                pe.setScene(GamePanel.activeGame.getGameManager().getActiveScene());
            }
            case "below" -> {
                System.out.println("Spawning belower!");
                BelowEnemy pe = new BelowEnemy();
                pe.setPosition(new Vector2D(500, 300));
                pe.setScene(GamePanel.activeGame.getGameManager().getActiveScene());
            }
            case "stop" -> {
                System.out.println("Stopping!");

                for (IGameObject o : GamePanel.activeGame.getGameManager().getActiveScene().getObjects()) {
                    if (o instanceof Enemy enemy) {
                        enemy.explode();
                    }
                }

                GamePanel.activeGame.getGameManager().enemies = 999;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(visible) {
            final char c = e.getKeyChar();
            if(c == '\b') {
                if(!command.isEmpty()) {
                    command = command.substring(0, command.length() - 1);
                }
            } else if (c >= 32 && c <= 126 ) {
                command += c;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        final int code = e.getKeyCode();

        if(code == KeyEvent.VK_BACK_QUOTE) {
            visible = !visible;
            command = "";
        } else if (visible && code == KeyEvent.VK_ENTER) {
            visible = false;
            handleCommand();
            command = "";
        }
    }
}
