package com.jbtits.geekbrains.lv2.lesson1.gui.sprites;

import javax.swing.*;
import java.awt.*;

/**
 * @author Nikolay Zaytsev
 */
public class Background extends Sprite {

    public static final int HALF_COLOR_AMPLITUDE = 100;
    private Color color;
    private float time;

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
        this.time += timeDelay;
        final int r = (int) ((Math.sin(time * .5) + 1) * HALF_COLOR_AMPLITUDE);
        final int g = (int) ((Math.sin(time * .25) + 1) * HALF_COLOR_AMPLITUDE);
        final int b = (int) ((Math.sin(time * .15) + 1) * HALF_COLOR_AMPLITUDE);
        return new Color(r, g, b);
    }
}
