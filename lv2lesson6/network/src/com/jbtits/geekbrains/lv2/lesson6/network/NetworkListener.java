package com.jbtits.geekbrains.lv2.lesson6.network;

import com.jbtits.geekbrains.lv2.lesson6.network.domain.NetworkMessage;

/**
 * @author Nikolay Zaytsev
 */
public interface NetworkListener<T extends NetworkMessage> {

    void onConnect(long clientId);

    void onMessageReceived(long clientId, T message);

    void onDisconnect(long clientId);
}
