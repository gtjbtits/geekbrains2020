package com.jbtits.geekbrains.lv2.lesson1.gui.sprites;

import javax.swing.*;
import java.awt.*;

/**
 * @author Nikolay Zaytsev
 */
public abstract class Sprite {

    private int x;
    private int y;
    private int width;
    private int height;

    private final boolean removable;

    protected Sprite(int x, int y, int width, int height, boolean removable) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.removable = removable;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected void setX(int x) {
        this.x = x;
    }

    protected void setY(int y) {
        this.y = y;
    }

    protected void setWidth(int width) {
        this.width = width;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public abstract void update(JPanel canvas, float timeDelay);

    public abstract void render(JPanel canvas, Graphics g);

    public boolean isRemovable() {
        return removable;
    }

}
