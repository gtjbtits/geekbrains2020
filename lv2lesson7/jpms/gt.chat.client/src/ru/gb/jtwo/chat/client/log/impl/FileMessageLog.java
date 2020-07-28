package ru.gb.jtwo.chat.client.log.impl;

import ru.gb.jtwo.chat.client.log.MessageLog;
import ru.gb.jtwo.chat.common.messages.BroadcastMessage;

import java.io.*;
import java.util.Objects;

public class FileMessageLog implements MessageLog {

    public static final String LOG_FILE_NAME = "lv2lesson7/jpms/gt.chat.client/client.bin.log";
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
    public void insert(BroadcastMessage message) {
        try (final OutputStream out = new FileOutputStream(logFile, true)) {
            final String outString = getSeparator() + message.getRawMessage();
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
    public BroadcastMessage[] read(int limit) {
        if (limit < 1) {
            throw new IllegalArgumentException("Parameter limit must be > 0");
        }
        final BroadcastMessage[] messages = new BroadcastMessage[limit];
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
                        messages[messageIdx++] = BroadcastMessage.fromRawMessage(new String(reversed));
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
                messages[messageIdx++] = BroadcastMessage.fromRawMessage(new String(readBytes.toByteArray()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (messageIdx == 0) {
            return new BroadcastMessage[] {};
        }
        final BroadcastMessage[] readMessages = new BroadcastMessage[messageIdx];
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

    private BroadcastMessage[] reverse(BroadcastMessage[] array) {
        final BroadcastMessage[] reversed = new BroadcastMessage[array.length];
        for (int i = 0; i < array.length; i++) {
            reversed[array.length - 1 - i] = array[i];
        }
        return reversed;
    }
}
