package ru.gb.jtwo.chat.client.log.impl;

import ru.gb.jtwo.chat.client.log.MessageLog;
import ru.gb.jtwo.chat.common.Library;
import ru.gb.jtwo.chat.common.messages.BroadcastServerMessage;
import ru.gb.jtwo.chat.common.messages.base.CommandWithParameters;

import java.io.*;

public class FileMessageLog implements MessageLog {

    public static final String LOG_FILE_NAME = "lv2lesson7/jpms/gt.chat.client/client.log.bin";
    public static final char SEPARATOR = '\0';
    public static final int SEPARATOR_LENGTH = 4;
    final File logFile;

    public FileMessageLog() {
        this.logFile = new File(LOG_FILE_NAME);
        try {
            this.logFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(BroadcastServerMessage message) {
        try (final OutputStream out = new FileOutputStream(logFile, true)) {
            final String outString = getSeparator() + message.toCommandString();
            out.write(outString.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getSeparator() {
        final byte[] separator = new byte[SEPARATOR_LENGTH];
        for (int i = 0; i < SEPARATOR_LENGTH; i++) {
            separator[i] = SEPARATOR;
        }
        return new String(separator);
    }

    @Override
    public BroadcastServerMessage[] read(int limit) {
        if (limit < 1) {
            throw new IllegalArgumentException("Parameter limit must be > 0");
        }
        final BroadcastServerMessage[] messages = new BroadcastServerMessage[limit];
        int messageIdx = 0;
        try (final RandomAccessFile file = new RandomAccessFile(logFile, "r")) {
            final long fileLength = logFile.length();
            int readByte;
            int readSeparators = 0;
            ByteArrayOutputStream readBytes = new ByteArrayOutputStream(1024);
            for (long i = fileLength - 1; i >= 0 ; i--) {
                file.seek(i);
                readByte = file.readByte();
                if (readByte == (byte) SEPARATOR) {
                    if (++readSeparators == SEPARATOR_LENGTH) {
                        final byte[] reversed = reverse(readBytes.toByteArray());
                        final CommandWithParameters command = Library.parse(new String(reversed)).orElseThrow();
                        messages[messageIdx++] = BroadcastServerMessage.constructWithParams(command.getParams());
                        if (messageIdx == limit) {
                            return reverse(messages);
                        }
                        readBytes.reset();
                        readSeparators = 0;
                    }
                } else {
                    readBytes.write(readByte);
                }
            }

            if (readBytes.size() > 0) {
                final CommandWithParameters command = Library.parse(new String(readBytes.toByteArray())).orElseThrow();
                messages[messageIdx++] = BroadcastServerMessage.constructWithParams(command.getParams());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (messageIdx == 0) {
            return new BroadcastServerMessage[] {};
        }
        final BroadcastServerMessage[] readMessages = new BroadcastServerMessage[messageIdx];
        System.arraycopy(messages, 0, readMessages, 0, messageIdx);
        return reverse(readMessages);
    }

    private byte[] reverse(byte[] bytes) {
        final byte[] reversed = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            reversed[bytes.length - 1 - i] = bytes[i];
        }
        return reversed;
    }

    private BroadcastServerMessage[] reverse(BroadcastServerMessage[] array) {
        final BroadcastServerMessage[] reversed = new BroadcastServerMessage[array.length];
        for (int i = 0; i < array.length; i++) {
            reversed[array.length - 1 - i] = array[i];
        }
        return reversed;
    }
}
