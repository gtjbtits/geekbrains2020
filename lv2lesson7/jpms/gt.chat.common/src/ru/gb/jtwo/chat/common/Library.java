package ru.gb.jtwo.chat.common;

import java.util.Objects;

public class Library {
    /*
    * /auth_request±login±password
    * /auth_accept±nickname
    * /auth_denied
    * /broadcast±timestamp±src±msg
    *
    * /msg_format_error±msg
    * */

    private static final String[] EMPTY_PARAMETERS = {};

    public static final String DELIMITER = "±";
    public static final String AUTH_REQUEST = "/auth_request";
    public static final String AUTH_ACCEPT = "/auth_accept";
    public static final String AUTH_DENIED = "/auth_denied";
    public static final String MSG_FORMAT_ERROR = "/msg_format_error";
    public static final String BROADCAST_CLIENT = "/bcast_client";
    public static final String BROADCAST_SERVER = "/bcast_server";

    public static String getAuthRequest(String login, String password) {
        return AUTH_REQUEST + DELIMITER + login + DELIMITER + password;
    }

    public static String getAuthAccept(String nickname) {
        return AUTH_ACCEPT + DELIMITER + nickname;
    }

    public static String getAuthDenied() {
        return AUTH_DENIED;
    }

    public static String getMsgFormatError(String message) {
        return MSG_FORMAT_ERROR + DELIMITER + message;
    }

    public static String getBroadcastServer(String src, String message) {
        return BROADCAST_SERVER + DELIMITER + System.currentTimeMillis() +
                DELIMITER + src + DELIMITER + message;
    }

    public static String getBroadcastClient(String message) {
        return BROADCAST_CLIENT + DELIMITER + message;
    }

    public static String getCommand(String message) {
        if (Objects.isNull(message)) {
            throw new NullPointerException("Can't get command from null message");
        }
        return message.split(DELIMITER)[0];
    }

    public static String[] getParameters(String message) {
        if (Objects.isNull(message)) {
            throw new NullPointerException("Can't get params from null message");
        }
        final String[] parts = message.split(DELIMITER);
        if (parts.length == 1) {
            return EMPTY_PARAMETERS;
        }
        final String[] parameters = new String[parts.length - 1];
        System.arraycopy(parts, 1, parameters, 0, parameters.length);
        return parameters;
    }

}
