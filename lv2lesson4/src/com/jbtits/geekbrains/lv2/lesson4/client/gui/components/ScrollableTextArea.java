package com.jbtits.geekbrains.lv2.lesson4.client.gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * @author Nikolay Zaytsev
 */
public class ScrollableTextArea extends JTextArea { // NOSONAR

    private final JScrollPane scrollPane;

    public ScrollableTextArea(int width, int height, boolean editable) {
        this.setEditable(editable);
        this.scrollPane = new JScrollPane(this);
        this.scrollPane.setPreferredSize(new Dimension(width, height));
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

}
