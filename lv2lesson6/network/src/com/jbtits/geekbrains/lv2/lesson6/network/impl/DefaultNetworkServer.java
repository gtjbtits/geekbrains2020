package com.jbtits.geekbrains.lv2.lesson6.network.impl;

import com.jbtits.geekbrains.lv2.lesson6.logging.CommonLoger;
import com.jbtits.geekbrains.lv2.lesson6.network.NetworkListener;
import com.jbtits.geekbrains.lv2.lesson6.network.NetworkServer;
import com.jbtits.geekbrains.lv2.lesson6.network.domain.NetworkMessage;
import com.jbtits.geekbrains.lv2.lesson6.network.domain.exeptions.ClientDisconnectProcessException;
import com.jbtits.geekbrains.lv2.lesson6.network.domain.exeptions.ClientNotFoundException;
import com.jbtits.geekbrains.lv2.lesson6.network.domain.exeptions.ServerException;
import com.jbtits.geekbrains.lv2.lesson6.network.socket.ServerSocketThread;
import com.jbtits.geekbrains.lv2.lesson6.network.socket.ServerSocketThreadListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Deque;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Nikolay Zaytsev
 */
public class DefaultNetworkServer<T extends NetworkMessage> implements NetworkServer<T>,
        ServerSocketThreadListener {

    private static final CommonLoger log = new CommonLoger("network");

    private final NetworkListener<T> listener;
    private final AtomicLong clientsCounter;
    private final ServerSocketThread serverSocketThread;
    private final WorkersController workersController;
    private final ConcurrentMap<Long, Socket> clients;
    private final Deque<OutboundMessage<T>> outboundMessages;

    public DefaultNetworkServer(NetworkListener<T> listener, int port, String name, int timeout) {
        this.listener = listener;
        this.serverSocketThread = new ServerSocketThread(this, name, port, timeout);
        this.workersController = new WorkersController();
        this.clients = new ConcurrentHashMap<>();
        this.outboundMessages = new ConcurrentLinkedDeque<>();
        this.clientsCounter = new AtomicLong(0);
    }

    @Override
    public void start() {
        this.serverSocketThread.start();
        this.workersController.start();
    }

    @Override
    public void stop() {
        this.serverSocketThread.interrupt();
        this.workersController.interrupt();
        try {
            this.serverSocketThread.join();
            this.workersController.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ServerException(e.getMessage(), e);
        }
        clients.keySet().forEach(this::disconnect);
    }

    @Override
    public void disconnect(long clientId) {
        if (!clients.containsKey(clientId)) {
            throw new ClientNotFoundException();
        }
        final Socket socket = clients.remove(clientId);
        if (!socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                throw new ClientDisconnectProcessException(e.getMessage(), e);
            }
        }
        listener.onDisconnect(clientId);
    }

    @Override
    public void sendMessage(long fromClientId, T message) {
        outboundMessages.addLast(new OutboundMessage<>(fromClientId, message));
    }

    @Override
    public void onServerStarted(ServerSocketThread thread) {
        // no-op
    }

    @Override
    public void onServerCreated(ServerSocketThread thread, ServerSocket server) {
        log.info("Server created");
    }

    @Override
    public void onServerTimeout(ServerSocketThread thread, ServerSocket server) {
        // no-op
    }

    @Override
    public void onSocketAccepted(ServerSocketThread thread, ServerSocket server, Socket socket) {
        final long id = clientsCounter.incrementAndGet();
        clients.put(id, socket);
        listener.onConnect(id);
    }

    @Override
    public void onServerException(ServerSocketThread thread, Throwable throwable) {
        this.stop();
        throw new ServerException(throwable.getMessage(), throwable);
    }

    @Override
    public void onServerStop(ServerSocketThread thread) {
        log.info("Server stopped");
    }

    private static class OutboundMessage<T extends NetworkMessage> {

        private final long clientId;
        private final T message;

        public OutboundMessage(long clientId, T message) {
            this.clientId = clientId;
            this.message = message;
        }

        public long getClientId() {
            return clientId;
        }

        public T getMessage() {
            return message;
        }
    }

    private class WorkersController extends Thread {

        @Override
        public void run() {
            while (!this.isInterrupted()) {
                sendMessage();
                receiveMessage();
            }
        }

        private void sendMessage() {
            final OutboundMessage<T> outboundMessage = outboundMessages.pollFirst();
            if (Objects.isNull(outboundMessage)) {
                return;
            }
            final long clientId = outboundMessage.getClientId();
            if (!clients.containsKey(clientId)) {
                throw new ClientNotFoundException();
            }
            final Socket socket = clients.get(clientId);
            try (final OutputStream outputStream = socket.getOutputStream();
                 final ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
                objectOutputStream.writeObject(outboundMessage.getMessage());
            } catch (IOException e) {
                log.warn(e, "Send message for client %d failed with message %s", clientId, e.getMessage());
            }
        }

        private void receiveMessage() {
            clients.forEach((clientId, socket) -> {
                try (final InputStream inputStream = socket.getInputStream();
                     final ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
                    socket.setSoTimeout(100);
                    final T message = (T) objectInputStream.readObject();
                    listener.onMessageReceived(clientId, message);
                } catch (SocketTimeoutException soe) {
                    // no-op
                } catch (IOException | ClassNotFoundException e) {
                    log.warn(e, "Receive message for client %d failed with message %s", clientId,
                            e.getMessage());
                }
            });
        }
    }
}
