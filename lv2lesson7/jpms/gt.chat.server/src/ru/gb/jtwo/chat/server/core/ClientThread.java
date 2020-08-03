package ru.gb.jtwo.chat.server.core;

import ru.gb.jtwo.chat.common.Library;
import ru.gb.jtwo.network.SocketThread;
import ru.gb.jtwo.network.SocketThreadListener;

import java.net.Socket;

public class ClientThread extends SocketThread {

    private String login;

    private String nickname;

    public ClientThread(SocketThreadListener listener, String name, Socket socket) {
        super(listener, name, socket);
    }

    public String getNickname() {
        return nickname;
    }

    public String getLogin() {
        return this.login;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isAuthorized() {
        return this.login != null;
    }

    public void authAccept(String login, String nickname) {
        this.login = login;
        this.nickname = nickname;
        sendMessage(Library.getAuthAccept(nickname));
    }

    public void authFail() {
        sendMessage(Library.getAuthDenied());
        close();
    }

    public void msgFormatError(String msg) {
        sendMessage(Library.getMsgFormatError(msg));
        close();
    }


}
