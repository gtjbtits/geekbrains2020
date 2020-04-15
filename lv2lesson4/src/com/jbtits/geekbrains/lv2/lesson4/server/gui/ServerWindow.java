package com.jbtits.geekbrains.lv2.lesson4.server.gui;

import com.jbtits.geekbrains.lv2.lesson4.gui.ExceptionHandler;
import com.jbtits.geekbrains.lv2.lesson4.server.ServerController;
import com.jbtits.geekbrains.lv2.lesson4.utils.GraphicalUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author Nikolay Zaytsev
 */
public class ServerWindow extends JFrame { // NOSONAR

    private static final int INITIAL_WIDTH = 300;
    private static final int INITIAL_HEIGHT = 100;
    private static final int LOCATION_OFFSET_X = INITIAL_WIDTH / 2;
    private static final int LOCATION_OFFSET_Y = 0;

    private final ServerController controller;

    public ServerWindow() {
        this.controller = new ServerController();
        this.setVisible(true);
        this.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
        this.setLocation(GraphicalUtils.getCentredLocation(this.getSize(), LOCATION_OFFSET_X, LOCATION_OFFSET_Y));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Chat server");
        ExceptionHandler.attach(this);
        this.addControls();
    }

    private void addControls() {
        final JRadioButton turnOnToggle = new JRadioButton("Turn on server");
        this.add(turnOnToggle);
        turnOnToggle.addActionListener(e -> {
            if (e.getSource() == turnOnToggle) {
                if (turnOnToggle.isSelected()) {
                    controller.turnOnServer();
                } else {
                    controller.turnOffServer();
                }
            }
        });
    }
}
