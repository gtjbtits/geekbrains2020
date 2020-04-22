package com.jbtits.geekbrains.lv2.lesson6.chat.server;

import com.jbtits.geekbrains.lv2.lesson6.network.NetworkListener;
import com.jbtits.geekbrains.lv2.lesson6.network.NetworkServer;
import com.jbtits.geekbrains.lv2.lesson6.network.domain.NetworkMessage;
import com.jbtits.geekbrains.lv2.lesson6.network.impl.DefaultNetworkServer;

/**
 * @author Nikolay Zaytsev
 */
public class ServerController implements NetworkListener<NetworkMessage> {

    private final NetworkServer<NetworkMessage> server;

    public ServerController() {
        this.server = new DefaultNetworkServer<>(this, 4000, "chat-server", 1000);
    }

    public void turnOnServer() {
        System.out.println("Turning on server...");
        this.server.start();
    }

    public void turnOffServer() {
        System.out.println("Turning off server...");
        this.server.stop();
    }

    @Override
    public void onConnect(long clientId) {
        System.out.println("Connected client " + clientId);
    }

    @Override
    public void onMessageReceived(long clientId, NetworkMessage message) {
        System.out.printf("Message %s received from client %d%n", message, clientId);
    }

    @Override
    public void onDisconnect(long clientId) {
        System.out.println("Disconnected client " + clientId);
    }
}
