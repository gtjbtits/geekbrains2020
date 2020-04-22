package com.jbtits.geekbrains.lv2.lesson6.network.transport;

import com.jbtits.geekbrains.lv2.lesson6.network.domain.CommonMessage;

import java.net.Socket;
import java.util.Deque;
import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author Nikolay Zaytsev
 */
public abstract class AbstractTransporter {

    private final BlockingDeque<CommonMessage> sendQueue;
    private final Deque<CommonMessage> receiveQueue;
    private final Thread sendThread;
    private final Thread receiveThread;
    private final ConcurrentMap<Integer, Socket> gates;

    protected AbstractTransporter() {
        sendQueue = new LinkedBlockingDeque<>();
        receiveQueue = new ConcurrentLinkedDeque<>();
        sendThread = new SendThread();
        receiveThread = new ReceiveThread();
        gates = new ConcurrentHashMap<>();
    }

    public void setUp() {
        sendThread.start();
        receiveThread.start();
    }

    private class SendThread extends Thread {

        @Override
        public void run() {
            while (!this.isInterrupted()) {
                final CommonMessage message;
                try {
                    message = sendQueue.pollFirst();
                    if (Objects.isNull(message)) {
                        System.out.println("Timeout. Going for the next round");
                        Thread.sleep(10);
                        continue;
                    }
                } catch (InterruptedException e) {
                    this.interrupt();
                    System.err.println("Send Thread interrupted. Continue");
                    continue;
                }
                if (!gates.containsKey(message.getFrom())) {
                    System.err.println("No gate for " + message.getFrom() + ". Skipped");
                    continue;
                }
                final Socket socket = gates.get(message.getFrom());
            }
        }
    }

    private static class ReceiveThread extends Thread {

        @Override
        public void run() {
            while (!this.isInterrupted()) {
                System.out.println("recv tick");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    this.interrupt();
                    e.printStackTrace();
                }
            }
        }
    }
}
