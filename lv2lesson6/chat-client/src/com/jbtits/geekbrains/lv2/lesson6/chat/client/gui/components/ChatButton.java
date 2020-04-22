package com.jbtits.geekbrains.lv2.lesson6.chat.client.gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * @author Nikolay Zaytsev
 */
public class ChatButton extends JButton { // NOSONAR

    public ChatButton(String name, int width, int height) {
        this.setText(name);
        this.setPreferredSize(new Dimension(width, height));
    }
}
