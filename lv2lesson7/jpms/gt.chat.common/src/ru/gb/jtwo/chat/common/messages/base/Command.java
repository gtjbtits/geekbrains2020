package ru.gb.jtwo.chat.common.messages.base;

import java.util.Collection;

public enum Command {
    AUTH_REQUEST("/auth_request"),
    AUTH_ACCEPT("/auth_accept"),
    AUTH_DENIED("/auth_denied"),
    MSG_FORMAT_ERROR("/msg_format_error"),
    BROADCAST_CLIENT("/bcast_client"),
    BROADCAST_SERVER("/bcast_server"),
    CHANGE_NICKNAME("/change_nickname");

    String cmd;

    Command(String cmd) {
        this.cmd = cmd;
    }

    public static Command parse(String cmd) {
        switch (cmd) {
            case "/auth_request":
                return AUTH_REQUEST;
            case "/auth_accept":
                return AUTH_ACCEPT;
            case "/auth_denied":
                return AUTH_DENIED;
            case "/msg_format_error":
                return MSG_FORMAT_ERROR;
            case "/bcast_client":
                return BROADCAST_CLIENT;
            case "/bcast_server":
                return BROADCAST_SERVER;
            case "/change_nickname":
            case "/cn":
                return CHANGE_NICKNAME;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return cmd;
    }
}
