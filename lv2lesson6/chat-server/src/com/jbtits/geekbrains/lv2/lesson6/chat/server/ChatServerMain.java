package com.jbtits.geekbrains.lv2.lesson6.chat.server;

import com.jbtits.geekbrains.lv2.lesson6.chat.server.gui.ServerWindow;

import javax.swing.*;

public class ChatServerMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ServerWindow::new);
    }
}
