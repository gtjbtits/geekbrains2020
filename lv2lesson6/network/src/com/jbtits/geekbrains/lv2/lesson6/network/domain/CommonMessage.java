package com.jbtits.geekbrains.lv2.lesson6.network.domain;

import java.io.Serializable;

/**
 * @author Nikolay Zaytsev
 */
public class CommonMessage implements Serializable {

    private final int from;

    public CommonMessage(int from) {
        this.from = from;
    }

    public int getFrom() {
        return from;
    }
}
