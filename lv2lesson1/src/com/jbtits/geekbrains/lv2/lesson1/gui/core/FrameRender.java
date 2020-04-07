package com.jbtits.geekbrains.lv2.lesson1.gui.core;

import com.jbtits.geekbrains.lv2.lesson1.gui.sprites.Sprite;

import javax.swing.*;
import java.awt.*;

/**
 * @author Nikolay Zaytsev
 */
public abstract class FrameRender extends JFrame { // NOSONAR

    protected final SpritesCollection sprites;

    protected FrameRender(Sprite[] sprites) {
        this.sprites = new SpritesCollection(sprites);
    }

    /**
     * Подготовить новый кадр для отображения
     *
     * @param canvas Холст для отрисовки
     * @param g Объект рисования
     * @param elapsedTime Задержка с момента отприсовки последнего кадра
     */
    public void renderNewFrame(JPanel canvas, Graphics g, float elapsedTime) {
        for (Sprite sprite: sprites.toArray()) {
            sprite.update(canvas, elapsedTime);
            sprite.render(canvas, g);
        }
    }
}
