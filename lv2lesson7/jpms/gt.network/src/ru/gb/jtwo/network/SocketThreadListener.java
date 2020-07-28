package ru.gb.jtwo.network;

import java.net.Socket;

public interface SocketThreadListener {

    void onSocketStart(SocketThread thread, Socket socket);
    void onSocketStop(SocketThread thread);
    void onSocketReady(SocketThread thread, Socket socket);
    void onReceiveString(SocketThread thread, Socket socket, String msg);
    void onSocketException(SocketThread thread, Throwable throwable);

}