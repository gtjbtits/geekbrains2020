package com.jbtits.geekbrains.lv2.lesson4.server;

import com.jbtits.geekbrains.lv2.lesson4.server.gui.ServerWindow;

import javax.swing.*;

public class ChatServerMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ServerWindow::new);
    }
}
