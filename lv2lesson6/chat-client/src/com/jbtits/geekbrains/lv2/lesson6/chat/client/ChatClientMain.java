package com.jbtits.geekbrains.lv2.lesson6.chat.client;

import com.jbtits.geekbrains.lv2.lesson6.chat.client.gui.ClientWindow;

import javax.swing.*;

/**
 * @author Nikolay Zaytsev
 */
public class ChatClientMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientWindow::new);
    }
}
