package com.jbtits.geekbrains.lv2.lesson2.domain.exceptions;

/**
 * @author Nikolay Zaytsev
 */
public class EmptyValueException extends IllegalArgumentException {

    public EmptyValueException(String source, int pos) {
        super("Empty value found in source '" + source + "' at char position " + pos);
    }
}
