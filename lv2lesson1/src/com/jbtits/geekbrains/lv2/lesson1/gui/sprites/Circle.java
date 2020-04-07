package com.jbtits.geekbrains.lv2.lesson1.gui.sprites;

import com.jbtits.geekbrains.lv2.lesson1.utils.Randomizer;

import javax.swing.*;
import java.awt.*;

/**
 * @author Nikolay Zaytsev
 */
public class Circle extends Sprite {

    private static final int SIZE_BOUND = 100;
    private static final int SPEED_BOUND = 500;

    private final Color color;
    private int vx;
    private int vy;

    public Circle() {
        this(0, 0, Randomizer.getRandomIntGreaterThanZero(SIZE_BOUND));
    }

    public Circle(int x, int y) {
        this(x, y, Randomizer.getRandomIntGreaterThanZero(SIZE_BOUND));
    }

    private Circle(int x, int y, int diameter) {
        super(x - diameter / 2, y - diameter / 2, diameter, diameter, true);
        this.color = Randomizer.getRandomColor();
        this.vx = Randomizer.getRandomIntGreaterThanZero(SPEED_BOUND);
        this.vy = Randomizer.getRandomIntGreaterThanZero(SPEED_BOUND);
    }

    @Override
    public void update(JPanel canvas, float timeDelay) {
        if ((this.vx > 0 && this.getX() + this.getWidth() > canvas.getWidth())
                || this.vx < 0 && this.getX() < 0) {
            this.vx = -vx;
        }
        if ((this.vy > 0 && this.getY() + this.getHeight() > canvas.getHeight())
                || this.vy < 0 && this.getY() < 0) {
            this.vy = -vy;
        }
        final int newX = this.getX() + (int) (vx * timeDelay);
        final int newY = this.getY() + (int) (vy * timeDelay);
        this.setX(newX);
        this.setY(newY);
    }

    @Override
    public void render(JPanel canvas, Graphics g) {
        g.setColor(this.color);
        g.fillOval(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
