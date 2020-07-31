package ru.gb.jtwo.chat.common.messages;

import ru.gb.jtwo.chat.common.Library;
import ru.gb.jtwo.chat.common.exceptions.MessageParametersCountMismatchException;
import ru.gb.jtwo.chat.common.messages.base.CommandString;

public class BroadcastClientMessage implements CommandString {

    private final String messageText;

    private BroadcastClientMessage(String messageText) {
        this.messageText = messageText;
    }

    public static BroadcastClientMessage constructWithParams(String[] params) {
        final int expectedParamsCount = 1;
        if (params.length != expectedParamsCount) {
            throw new MessageParametersCountMismatchException(expectedParamsCount, params.length);
        }
        return new BroadcastClientMessage(params[0]);
    }

    public String getMessageText() {
        return messageText;
    }

    @Override
    public String toPrettyString() {
        return messageText;
    }

    @Override
    public String toCommandString() {
        return Library.getBroadcastClient(this.messageText);
    }
}
