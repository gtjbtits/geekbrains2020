package com.jbtits.geekbrains.lv2.lesson4.client;

import com.jbtits.geekbrains.lv2.lesson4.client.gui.listeners.MessageListener;
import com.jbtits.geekbrains.lv2.lesson4.logging.ChatLogger;

/**
 * @author Nikolay Zaytsev
 */
public class ClientController implements MessageListener {

    private final ChatLogger log = new ChatLogger("client");

    @Override
    public void onNewMessage(String message) {
        log.info(message);
    }
}
