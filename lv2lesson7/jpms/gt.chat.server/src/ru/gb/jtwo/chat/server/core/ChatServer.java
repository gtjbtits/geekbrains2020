package ru.gb.jtwo.chat.server.core;

import ru.gb.jtwo.chat.common.Library;
import ru.gb.jtwo.chat.common.messages.AuthRequestMessage;
import ru.gb.jtwo.chat.common.messages.BroadcastClientMessage;
import ru.gb.jtwo.chat.common.messages.ChangeNicknameMessage;
import ru.gb.jtwo.chat.common.messages.base.CommandWithParameters;
import ru.gb.jtwo.network.ServerSocketThread;
import ru.gb.jtwo.network.ServerSocketThreadListener;
import ru.gb.jtwo.network.SocketThread;
import ru.gb.jtwo.network.SocketThreadListener;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {

    ServerSocketThread server;
    ChatServerListener listener;
    Vector<SocketThread> clients = new Vector<>();

    public ChatServer(ChatServerListener listener) {
        this.listener = listener;
    }

    public void start(int port) {
        if (server != null && server.isAlive())
            putLog("Already running");
        else
            server = new ServerSocketThread(this, "Server", port, 2000);
    }

    public void stop() {
        if (server == null || !server.isAlive()) {
            putLog("Nothing to stop");
        } else {
            server.interrupt();
        }
    }

    private void putLog(String msg) {
        listener.onChatServerMessage(msg);
    }

    /**
     * Server Socket Thread Listener methods
     * */

    @Override
    public void onServerStarted(ServerSocketThread thread) {
        putLog("Server thread started");
        SqlClient.connect();
    }

    @Override
    public void onServerCreated(ServerSocketThread thread, ServerSocket server) {
        putLog("Server socket started");
    }

    @Override
    public void onServerTimeout(ServerSocketThread thread, ServerSocket server) {
        //putLog("Server timeout");
    }

    @Override
    public void onSocketAccepted(ServerSocketThread thread, ServerSocket server, Socket socket) {
        putLog("Client connected");
        String name = "SocketThread " + socket.getInetAddress() + ":" + socket.getPort();
        new ClientThread(this, name, socket);
    }

    @Override
    public void onServerException(ServerSocketThread thread, Throwable throwable) {
        putLog("Server exception");
        throwable.printStackTrace();
    }

    @Override
    public void onServerStop(ServerSocketThread thread) {
        putLog("Server thread stopped");
        SqlClient.disconnect();
    }

    /**
     * Socket Thread Listener methods
     * */

    @Override
    public void onSocketStart(SocketThread thread, Socket socket) {
        putLog("Socket started");
    }

    @Override
    public void onSocketStop(SocketThread thread) {
        putLog("Socket stopped");
        clients.remove(thread);
    }

    @Override
    public void onSocketReady(SocketThread thread, Socket socket) {
        putLog("Socket ready");
        clients.add(thread);
    }

    @Override
    public void onReceiveString(SocketThread thread, Socket socket, String msg) {
        handleMessage((ClientThread) thread, msg);
    }

    @Override
    public void onSocketException(SocketThread thread, Throwable throwable) {
        throwable.printStackTrace();
    }

    void handleAuthRequest(ClientThread client, AuthRequestMessage authRequestMessage) {
        String nickname = SqlClient.getNickname(authRequestMessage.getUsername(), authRequestMessage.getPassword());
        if (nickname == null) {
            putLog("Invalid login attempt: " + authRequestMessage.getUsername());
            client.authFail();
            return;
        }
        client.authAccept(authRequestMessage.getUsername(), nickname);
        sendToAllAuthorizedClients(Library.getBroadcastServer(nickname + " connected"));
    }

    void handleChangeNickname(ClientThread client, ChangeNicknameMessage changeNicknameMessage) {
        if (!SqlClient.changeNickname(client.getLogin(), changeNicknameMessage.getNickname())) {
            putLog("Nickname was not updated");
            client.authFail();
            return;
        }
        sendToAllAuthorizedClients(Library.getBroadcastServer(client.getNickname() + " changed nickname to "
                + changeNicknameMessage.getNickname()));
        client.changeNickname(changeNicknameMessage.getNickname());
    }

    private void handleMessage(ClientThread client, String msg) {
        Library.parse(msg).ifPresentOrElse(command -> {
            try {
                handleCommand(client, command);
            } catch (RuntimeException e) {
                putLog("Can't extract command from message " + msg);
                client.msgFormatError(msg);
            }
        }, () -> client.msgFormatError(msg));
    }

    private void handleCommand(ClientThread client, CommandWithParameters command) {
        switch (command.getCommand()) {
            case AUTH_REQUEST:
                handleAuthRequest(client, AuthRequestMessage.constructWithParams(command.getParams()));
                break;
            case BROADCAST_CLIENT:
                final BroadcastClientMessage broadcastClientMessage = BroadcastClientMessage
                        .constructWithParams(command.getParams());
                if (!client.isAuthorized()) {
                    putLog("Forbidden: bcast messages available only for authorized users");
                    client.authFail();
                    return;
                }
                sendToAllAuthorizedClients(Library.getBroadcastServer(client.getNickname(),
                        broadcastClientMessage.getMessageText()));
                break;
            case CHANGE_NICKNAME:
                handleChangeNickname(client, ChangeNicknameMessage.constructWithParams(command.getParams()));
                break;
            default:
                putLog("Unknown command: " + command.getCommand());
                client.msgFormatError(command.getCommand().toString());
        }
    }

    private void sendToAllAuthorizedClients(String msg) {
        for (SocketThread socketThread : clients) {
            ClientThread client = (ClientThread) socketThread;
            if (!client.isAuthorized()) continue;
            client.sendMessage(msg);
        }
    }
}
