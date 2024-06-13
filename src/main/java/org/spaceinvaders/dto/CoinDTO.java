package org.spaceinvaders.dto;

import java.awt.*;

/**
 * Coin data transfer object
 */
public class CoinDTO {
    public int value;
    public Color color;

    public CoinDTO(int value, Color color) {
        this.value = value;
        this.color = color;
    }
}
