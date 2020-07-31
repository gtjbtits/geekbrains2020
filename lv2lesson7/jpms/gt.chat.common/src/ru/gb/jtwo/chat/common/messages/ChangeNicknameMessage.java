package ru.gb.jtwo.chat.common.messages;

import ru.gb.jtwo.chat.common.Library;
import ru.gb.jtwo.chat.common.exceptions.MessageParametersCountMismatchException;
import ru.gb.jtwo.chat.common.messages.base.CommandString;

public class ChangeNicknameMessage implements CommandString {

    private final String nickname;

    public static ChangeNicknameMessage constructWithParams(String[] params) {
        final int expectedParamsCount = 1;
        if (params.length != expectedParamsCount) {
            throw new MessageParametersCountMismatchException(expectedParamsCount, params.length);
        }
        return new ChangeNicknameMessage(params[0]);
    }

    private ChangeNicknameMessage(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toPrettyString() {
        return "Change nickname request. New nickname: " + this.nickname;
    }

    @Override
    public String toCommandString() {
        return Library.getChangeNickname(this.nickname);
    }
}
