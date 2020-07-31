package ru.gb.jtwo.chat.common.messages.base;

import java.util.Arrays;

import static ru.gb.jtwo.chat.common.Library.DEFAULT_COMMAND_DELIMITER;

public class CommandWithParameters implements CommandString {

    private final String[] params;
    private final Command command;

    public CommandWithParameters(Command command, String[] params) {
        this.command = command;
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }

    public Command getCommand() {
        return command;
    }

    @Override
    public String toPrettyString() {
        return String.format("%s with params %s", this.command.toString(), Arrays.toString(this.params));
    }

    @Override
    public String toCommandString() {
        return this.command.toString() + DEFAULT_COMMAND_DELIMITER + String.join(DEFAULT_COMMAND_DELIMITER, this.params);
    }

    @Override
    public String toString() {
        return this.toPrettyString();
    }
}
