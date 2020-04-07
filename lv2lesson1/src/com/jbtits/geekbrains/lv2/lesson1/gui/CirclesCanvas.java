package com.jbtits.geekbrains.lv2.lesson1.gui;

import com.jbtits.geekbrains.lv2.lesson1.gui.core.FrameRender;

import javax.swing.*;
import java.awt.*;

/**
 * @author Nikolay Zaytsev
 */
public class CirclesCanvas extends JPanel {

    public static final int DELAY_MS_BETWEEN_FRAMES = 16;

    private final FrameRender render;
    private long lastFrameTimestamp;

    CirclesCanvas(FrameRender render) {
        this.render = render;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final float elapsedTimeInSeconds = calculateElapsedTime() * .000000001f;
        render.renderNewFrame(this, g, elapsedTimeInSeconds);
        delay();
        this.repaint();
    }

    private long calculateElapsedTime() {
        final long elapsedTime;
        final long currentTime = System.nanoTime();
        if (lastFrameTimestamp != 0) {
            elapsedTime = currentTime - lastFrameTimestamp;
        } else {
            elapsedTime = 0;
        }
        lastFrameTimestamp = currentTime;
        return elapsedTime;
    }

    private void delay() {
        try {
            Thread.sleep(DELAY_MS_BETWEEN_FRAMES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
