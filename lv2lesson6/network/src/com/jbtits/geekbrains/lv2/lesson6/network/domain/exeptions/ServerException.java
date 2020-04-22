package com.jbtits.geekbrains.lv2.lesson6.network.domain.exeptions;

/**
 * @author Nikolay Zaytsev
 */
public class ServerException extends RuntimeException {
    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
