package com.jbtits.geekbrains.lv2.lesson4.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author Nikolay Zaytsev
 */
public class ChatLogger {

    private static final String LOG_FILE_EXT = ".log";

    private final Logger logger;

    public ChatLogger(String name) {
        this.logger = Logger.getLogger(name);
        final FileHandler fileHandler;
        try {
            fileHandler = new FileHandler(name + LOG_FILE_EXT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        fileHandler.setFormatter(simpleFormatter);
        this.logger.addHandler(fileHandler);
    }

    public void info(String message) {
        this.logger.info(message);
    }
}
