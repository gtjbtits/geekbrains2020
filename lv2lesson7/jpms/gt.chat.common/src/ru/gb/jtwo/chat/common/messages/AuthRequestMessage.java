package ru.gb.jtwo.chat.common.messages;

import ru.gb.jtwo.chat.common.Library;
import ru.gb.jtwo.chat.common.exceptions.MessageParametersCountMismatchException;
import ru.gb.jtwo.chat.common.messages.base.CommandString;

public class AuthRequestMessage implements CommandString {

    private final String username;
    private final String password;

    public static AuthRequestMessage constructWithParams(String[] params) {
        final int expectedParamsCount = 2;
        if (params.length != expectedParamsCount) {
            throw new MessageParametersCountMismatchException(expectedParamsCount, params.length);
        }
        return new AuthRequestMessage(params[0], params[1]);
    }

    private AuthRequestMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toPrettyString() {
        return username + "'s auth request";
    }

    @Override
    public String toCommandString() {
        return Library.getAuthRequest(this.username, this.password);
    }
}
