package com.jbtits.geekbrains.lv2.lesson6.network.domain.exeptions;

import java.io.IOException;

/**
 * @author Nikolay Zaytsev
 */
public class ClientDisconnectProcessException extends RuntimeException {

    public ClientDisconnectProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
