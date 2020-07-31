package ru.gb.jtwo.chat.common.messages;

import ru.gb.jtwo.chat.common.Library;
import ru.gb.jtwo.chat.common.exceptions.MessageParametersCountMismatchException;
import ru.gb.jtwo.chat.common.exceptions.MessageParseException;
import ru.gb.jtwo.chat.common.messages.base.CommandString;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class BroadcastServerMessage implements CommandString {

    private final String from;
    private final long timestamp;
    private final String messageText;

    public static BroadcastServerMessage constructWithParams(String[] params) {
        final int expectedParamsCount = 3;
        if (params.length != expectedParamsCount) {
            throw new MessageParametersCountMismatchException(expectedParamsCount, params.length);
        }
        final long timestamp;
        try {
            timestamp = Long.parseLong(params[0]);
        } catch (NumberFormatException e) {
            throw new MessageParseException("Timestamp wrong format: " + params[0]);
        }
        final String from = params[1];
        final String messageText = params[2];
        return new BroadcastServerMessage(from, timestamp, messageText);
    }

    private BroadcastServerMessage(String from, long timestamp, String messageText) {
        this.from = from;
        this.timestamp = timestamp;
        this.messageText = messageText;
    }

    private String prettyDate(long timestamp) {
        final Date date = Date.from(Instant.ofEpochMilli(timestamp));
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(date);
    }

    @Override
    public String toPrettyString() {
        return String.format("%s %s:%n%s", prettyDate(timestamp), from, messageText);
    }

    @Override
    public String toCommandString() {
        return Library.getBroadcastServer(this.from, this.timestamp, this.messageText);
    }
}
