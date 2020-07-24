package ru.gb.jtwo.chat.client.log;

import ru.gb.jtwo.chat.common.messages.BroadcastMessage;

public interface MessageLog {

    void insert(BroadcastMessage message);

    BroadcastMessage[] read(int limit);
}
