package com.jbtits.geekbrains.lv2.lesson4.utils;

import java.awt.*;

/**
 * @author Nikolay Zaytsev
 */
public class GraphicalUtils {

    private GraphicalUtils() {

    }

    public static Point getCentredLocation(Dimension window, int offsetX, int offsetY) {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int x = (int) ((screenSize.getWidth() - window.getSize().getWidth()) * .5) + offsetX;
        final int y = (int) ((screenSize.getHeight() - window.getSize().getHeight()) * .5) + offsetY;
        return new Point(x, y);
    }
}
