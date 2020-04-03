package com.jbtits.geekbrains.lv2.lesson1.gui;

import com.jbtits.geekbrains.lv2.lesson1.gui.core.FrameRender;
import com.jbtits.geekbrains.lv2.lesson1.gui.sprites.Background;
import com.jbtits.geekbrains.lv2.lesson1.gui.sprites.Circle;
import com.jbtits.geekbrains.lv2.lesson1.gui.sprites.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Objects;

/**
 * @author Nikolay Zaytsev
 */
public class CirclesWindow extends FrameRender { // NOSONAR

    private static final int INITIAL_WINDOW_WIDTH = 600;
    private static final int INITIAL_WINDOW_HEIGHT = 400;

    public CirclesWindow() {
        super(CirclesWindow.prepareSprites(20));
        this.setVisible(true);
        this.setSize(new Dimension(INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new CirclesCanvas(this));
    }

    private static Sprite[] prepareSprites(int amount) {
        final Sprite[] sprites = new Sprite[amount + 1];
        sprites[0] = new Background();
        for (int i = 1; i < sprites.length; i++) {
            sprites[i] = new Circle();
        }
        return sprites;
    }

    @Override
    public void handleMousePressedEvent(JPanel canvas, MouseEvent event) {
        final int mouseX = event.getX();
        final int mouseY = event.getY();
        final int button = event.getButton();
        if (button == MouseEvent.BUTTON1) {
            addSprite(mouseX, mouseY);
        } else if (button == MouseEvent.BUTTON3) {
            removeSprite(mouseX, mouseY);
        }
    }

    private void addSprite(int mouseX, int mouseY) {
        sprites.add(new Circle(mouseX, mouseY));
    }

    private void removeSprite(int mouseX, int mouseY) {
        final Sprite sprite = findSpriteAtPoint(mouseX, mouseY);
        if (Objects.nonNull(sprite) && sprite.isRemovable()) {
            sprites.remove(sprite);
        }
    }

    private Sprite findSpriteAtPoint(int x, int y) {
        final Sprite[] spritesArray = sprites.toArray();
        for (int i = spritesArray.length - 1; i >= 0; i--) {
            if (x >= spritesArray[i].getX() && x - spritesArray[i].getX() <= spritesArray[i].getWidth()
                    && y >= spritesArray[i].getY() && y - spritesArray[i].getY() <= spritesArray[i].getHeight()) {
                return spritesArray[i];
            }
        }
        return null;
    }
}
