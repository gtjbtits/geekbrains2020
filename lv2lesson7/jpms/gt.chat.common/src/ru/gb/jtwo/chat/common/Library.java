package ru.gb.jtwo.chat.common;

import ru.gb.jtwo.chat.common.messages.base.Command;
import ru.gb.jtwo.chat.common.messages.base.CommandWithParameters;

import java.util.Objects;
import java.util.Optional;

public class Library {

    public static final String DEFAULT_COMMAND_DELIMITER = "±";

    private static final String AUTH_REQUEST = Command.AUTH_REQUEST.toString();
    private static final String AUTH_ACCEPT = Command.AUTH_ACCEPT.toString();
    private static final String AUTH_DENIED = Command.AUTH_DENIED.toString();
    private static final String MSG_FORMAT_ERROR = Command.MSG_FORMAT_ERROR.toString();
    private static final String BROADCAST_CLIENT = Command.BROADCAST_CLIENT.toString();
    private static final String BROADCAST_SERVER = Command.BROADCAST_SERVER.toString();
    private static final String CHANGE_NICKNAME = Command.CHANGE_NICKNAME.toString();

    private static final String FROM_SERVER = "Server";

    private Library() {}

    public static String getAuthRequest(String login, String password) {
        return AUTH_REQUEST + DEFAULT_COMMAND_DELIMITER + login + DEFAULT_COMMAND_DELIMITER + password;
    }

    public static String getAuthAccept(String nickname) {
        return AUTH_ACCEPT + DEFAULT_COMMAND_DELIMITER + nickname;
    }

    public static String getAuthDenied() {
        return AUTH_DENIED;
    }

    public static String getMsgFormatError(String message) {
        return MSG_FORMAT_ERROR + DEFAULT_COMMAND_DELIMITER + message;
    }

    public static String getBroadcastServer(String src, long timestamp, String message) {
        return BROADCAST_SERVER + DEFAULT_COMMAND_DELIMITER + timestamp +
                DEFAULT_COMMAND_DELIMITER + src + DEFAULT_COMMAND_DELIMITER + message;
    }

    public static String getBroadcastServer(String src, String message) {
        return Library.getBroadcastServer(src, System.currentTimeMillis(), message);
    }

    public static String getBroadcastServer(String message) {
        return Library.getBroadcastServer(FROM_SERVER, System.currentTimeMillis(), message);
    }

    public static String getBroadcastClient(String message) {
        return BROADCAST_CLIENT + DEFAULT_COMMAND_DELIMITER + message;
    }

    public static String getChangeNickname(String nickname) {
        return CHANGE_NICKNAME + DEFAULT_COMMAND_DELIMITER + nickname;
    }

    public static Optional<CommandWithParameters> parse(String message, String delimiter) {
        if (Objects.isNull(message)) {
            throw new IllegalArgumentException("Message can't be null");
        }
        if (message.isEmpty()) {
            return Optional.empty();
        }
        final String[] parts = message.split(delimiter);
        final Command command = Command.parse(parts[0]);
        if (command == null) {
            return Optional.empty();
        }
        final String[] params = new String[parts.length - 1];
        System.arraycopy(parts, 1, params, 0, params.length);
        return Optional.of(new CommandWithParameters(command, params));
    }

    public static Optional<CommandWithParameters> parse(String message) {
        return Library.parse(message, DEFAULT_COMMAND_DELIMITER);
    }

}
