package com.jbtits.geekbrains.lv2.lesson6.network;

import com.jbtits.geekbrains.lv2.lesson6.network.domain.NetworkMessage;

/**
 * @author Nikolay Zaytsev
 */
public interface NetworkServer<T extends NetworkMessage> {

    void start();

    void stop();

    void disconnect(long clientId);

    void sendMessage(long fromClientId, T message);
}
