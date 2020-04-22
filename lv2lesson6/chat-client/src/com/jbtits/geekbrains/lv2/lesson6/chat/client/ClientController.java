package com.jbtits.geekbrains.lv2.lesson6.chat.client;

import com.jbtits.geekbrains.lv2.lesson6.chat.client.gui.listeners.MessageListener;
import com.jbtits.geekbrains.lv2.lesson6.logging.CommonLoger;

/**
 * @author Nikolay Zaytsev
 */
public class ClientController implements MessageListener {

    private final CommonLoger log = new CommonLoger("client");

    @Override
    public void onNewMessage(String message) {
        log.info(message);
    }
}
