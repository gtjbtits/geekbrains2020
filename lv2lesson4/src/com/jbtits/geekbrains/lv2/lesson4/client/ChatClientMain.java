package com.jbtits.geekbrains.lv2.lesson4.client;

import com.jbtits.geekbrains.lv2.lesson4.client.gui.ClientWindow;

import javax.swing.*;

/**
 * @author Nikolay Zaytsev
 */
public class ChatClientMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientWindow::new);
    }
}
