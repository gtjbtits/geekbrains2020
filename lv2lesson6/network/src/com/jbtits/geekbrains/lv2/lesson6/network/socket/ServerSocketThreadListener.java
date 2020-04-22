package com.jbtits.geekbrains.lv2.lesson6.network.socket;

import com.jbtits.geekbrains.lv2.lesson6.network.socket.ServerSocketThread;

import java.net.ServerSocket;
import java.net.Socket;

public interface ServerSocketThreadListener {

    void onServerStarted(ServerSocketThread thread);
    void onServerCreated(ServerSocketThread thread, ServerSocket server);
    void onServerTimeout(ServerSocketThread thread, ServerSocket server);
    void onSocketAccepted(ServerSocketThread thread, ServerSocket server, Socket socket);
    void onServerException(ServerSocketThread thread, Throwable throwable);
    void onServerStop(ServerSocketThread thread);

}
