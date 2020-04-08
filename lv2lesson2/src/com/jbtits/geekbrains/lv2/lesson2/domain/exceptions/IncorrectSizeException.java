package com.jbtits.geekbrains.lv2.lesson2.domain.exceptions;

/**
 * @author Nikolay Zaytsev
 */
public class IncorrectSizeException extends IllegalArgumentException {

    public IncorrectSizeException(int expectedSize, int actualSize) {
        super("Expected size: " + expectedSize + ", actualSize: " + actualSize);
    }
}
