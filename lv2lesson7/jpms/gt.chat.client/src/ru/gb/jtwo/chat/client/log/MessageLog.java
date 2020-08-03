package ru.gb.jtwo.chat.client.log;

import ru.gb.jtwo.chat.common.messages.BroadcastServerMessage;

public interface MessageLog {

    void insert(BroadcastServerMessage message);

    BroadcastServerMessage[] read(int limit);
}
