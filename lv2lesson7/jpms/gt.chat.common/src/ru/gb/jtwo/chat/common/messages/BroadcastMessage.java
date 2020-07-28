package ru.gb.jtwo.chat.common.messages;

import ru.gb.jtwo.chat.common.Library;
import ru.gb.jtwo.chat.common.exceptions.MessageParseException;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class BroadcastMessage {

    private final String from;
    private final long timestamp;
    private final String messageText;

    private final String rawMessage;

    public static BroadcastMessage fromRawMessage(String rawMessage) {
        final String[] parameters = Library.getParameters(rawMessage);
        if (parameters.length != 3) {
            throw new MessageParseException();
        }
        final long timestamp;
        try {
            timestamp = Long.parseLong(parameters[0]);
        } catch (NumberFormatException e) {
            throw new MessageParseException();
        }
        final String from = parameters[1];
        final String messageText = parameters[2];
        return new BroadcastMessage(rawMessage, from, timestamp, messageText);
    }

    private BroadcastMessage(String rawMessage, String from, long timestamp, String messageText) {
        this.rawMessage = rawMessage;
        this.from = from;
        this.timestamp = timestamp;
        this.messageText = messageText;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    private String prettyDate(long timestamp) {
        final Date date = Date.from(Instant.ofEpochMilli(timestamp));
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(date);
    }

    @Override
    public String toString() {
        return String.format("%s %s:%n%s", prettyDate(timestamp), from, messageText);
    }
}
