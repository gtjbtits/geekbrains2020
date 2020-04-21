package com.jbtits.geekbrains.lv2.lesson4.client.gui;

import com.jbtits.geekbrains.lv2.lesson4.client.ClientController;
import com.jbtits.geekbrains.lv2.lesson4.client.gui.components.ChatButton;
import com.jbtits.geekbrains.lv2.lesson4.client.gui.components.ChatPanel;
import com.jbtits.geekbrains.lv2.lesson4.client.gui.components.InputTextArea;
import com.jbtits.geekbrains.lv2.lesson4.client.gui.components.ScrollableTextArea;
import com.jbtits.geekbrains.lv2.lesson4.client.gui.listeners.MessageListener;
import com.jbtits.geekbrains.lv2.lesson4.gui.ExceptionHandler;
import com.jbtits.geekbrains.lv2.lesson4.utils.GraphicalUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author Nikolay Zaytsev
 */
public class ClientWindow extends JFrame { // NOSONAR

    private static final int INITIAL_WIDTH = 400;
    private static final int INITIAL_HEIGHT = 300;
    private static final int LOCATION_OFFSET_X = -INITIAL_WIDTH / 2;
    private static final int LOCATION_OFFSET_Y = 0;

    private final ClientController controller;

    public ClientWindow() {
        this.controller = new ClientController();
        this.setVisible(true);
        this.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
        this.setLocation(GraphicalUtils.getCentredLocation(this.getSize(), LOCATION_OFFSET_X, LOCATION_OFFSET_Y));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Chat client");
        ExceptionHandler.attach(this);
        populateComponents();
    }

    private void populateComponents() {
        final JPanel viewport = new ChatPanel();
        final JPanel controls = new ChatPanel();
        final ScrollableTextArea log = new ScrollableTextArea(300, 250, false);
        final InputTextArea input = new InputTextArea(300, 50,
                message -> {
                    if (log.getText().isEmpty()) {
                        log.setText(String.format("%s%s", log.getText(), message));
                    } else {
                        log.setText(String.format("%s%n%s", log.getText(), message));
                    }
                }, controller);
        final ScrollableTextArea userList = new ScrollableTextArea(100, 280, false);
        final JButton connectToggle = new ChatButton("Connect", 50, 20);

        viewport.add(log.getScrollPane(), BorderLayout.CENTER);
        viewport.add(input.getScrollPane(), BorderLayout.SOUTH);
        controls.add(connectToggle, BorderLayout.NORTH);
        controls.add(userList.getScrollPane(), BorderLayout.CENTER);
        this.add(viewport, BorderLayout.CENTER);
        this.add(controls, BorderLayout.EAST);
    }
}
