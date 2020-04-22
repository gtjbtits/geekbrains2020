package com.jbtits.geekbrains.lv2.lesson6.network.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerSocketThread extends Thread {

    private final int port;
    private final int timeout;
    ServerSocketThreadListener listener;

    public ServerSocketThread(ServerSocketThreadListener listener, String name, int port, int timeout) {
        super(name);
        this.port = port;
        this.timeout = timeout;
        this.listener = listener;
    }

    @Override
    public void run() {
        listener.onServerStarted(this);
        try (ServerSocket server = new ServerSocket(port)) {
            server.setSoTimeout(timeout);
            listener.onServerCreated(this, server);
            while (!isInterrupted()) {
                Socket s;
                try {
                    s = server.accept();
                } catch (SocketTimeoutException e) {
                    listener.onServerTimeout(this, server);
                    continue;
                }
                listener.onSocketAccepted(this, server, s);
            }
        } catch (IOException e) {
            listener.onServerException(this, e);
        } finally {
            listener.onServerStop(this);
        }
    }
}
