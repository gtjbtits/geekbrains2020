package ru.gb.jtwo.chat.common.exceptions;

public class MessageParametersCountMismatchException extends MessageParseException {

    public MessageParametersCountMismatchException(int expected, int actual) {
        super(String.format("Expected count is %d, actual is %d", expected, actual));
    }
}
