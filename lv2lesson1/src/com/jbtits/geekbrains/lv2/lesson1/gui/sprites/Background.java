package com.jbtits.geekbrains.lv2.lesson1.gui.sprites;

import javax.swing.*;
import java.awt.*;

/**
 * @author Nikolay Zaytsev
 */
public class Background extends Sprite {

    public static final int COLOR_CHANGE_SPEED = 100;
    private Color color;

    public Background() {
        super(0, 0, 0, 0, false);
        color = Color.BLACK;
    }

    @Override
    public void update(JPanel canvas, float timeDelay) {
        this.setHeight(canvas.getHeight());
        this.setWidth(canvas.getWidth());
        this.color = changeColor(timeDelay);
    }

    @Override
    public void render(JPanel canvas, Graphics g) {
        g.setColor(this.color);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    private Color changeColor(float timeDelay) {
        final int r = (this.color.getRed() + (int) (timeDelay * COLOR_CHANGE_SPEED)) & 0xFF;
        final int g = (this.color.getGreen() + (int) (timeDelay * COLOR_CHANGE_SPEED)) & 0xFF;
        final int b = (this.color.getBlue() + (int) (timeDelay * COLOR_CHANGE_SPEED)) & 0xFF;
        return new Color(r, g, b);
    }
}
