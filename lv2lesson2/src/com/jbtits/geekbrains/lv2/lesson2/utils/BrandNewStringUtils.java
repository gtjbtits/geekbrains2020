package com.jbtits.geekbrains.lv2.lesson2.utils;

import com.jbtits.geekbrains.lv2.lesson2.domain.exceptions.EmptyValueException;
import com.jbtits.geekbrains.lv2.lesson2.domain.exceptions.IncorrectSizeException;

import java.util.Objects;

/**
 * @author Nikolay Zaytsev
 */
public class BrandNewStringUtils {

    private BrandNewStringUtils() {

    }

    /**
     * Разбивает строку на массив подстрок по регулярному выражению
     *
     * Размер результирующего массива должен быть заранее известен и задан через параметр size. Если количество подстрок
     * при разбиении не будет равно значению size, то будет выброшено исключение {@link IncorrectSizeException}
     *
     * @param str Исходная строка
     * @param size Размер результирующего массива
     * @param regex Регулярное выражение, описывающие разделяющую последовательность символов
     * @return Массив подстрок исходной сроки
     */
    static String[] split(String str, int size, String regex) {
        final String[] substrings = str.split(regex);
        if (substrings.length != size) {
            throw new IncorrectSizeException(size, substrings.length);
        }
        return substrings;
    }

    /**
     * Разбивает строку на массив подстрок по символу-разделителю
     *
     * Размер результирующего массива должен быть заранее известен и задан через параметр size. Если количество подстрок
     * при разбиении не будет равно значению size, то будет выброшено исключение {@link IncorrectSizeException}
     *
     * FIXME: Функция эквивалентно обрабатывает последовательности '1 2 3' и '1 2 3 ' для size=3
     *
     * @param str Исходная строка
     * @param size Размер результирующего массива
     * @param separator Разделитель
     * @return Массив подстрок исходной сроки
     */
    static String[] split(String str, int size, char separator) {
        if (Objects.isNull(str) || size < 1) {
            throw new IllegalArgumentException("Null string argument or incorrect size");
        }
        final String[] substrings = new String[size];
        int substrIdx = 0;
        int offset = 0;
        for (int i = 0; i < str.length(); i++) {
            if (substrIdx == size) {
                throw new IncorrectSizeException(size, substrIdx + 1);
            }
            if (i == str.length() - 1 && str.charAt(i) != separator) {
                substrings[substrIdx++] = str.substring(offset);
                continue;
            }
            if (str.charAt(i) == separator) {
                if (i == offset) {
                    throw new EmptyValueException(str, i);
                }
                substrings[substrIdx++] = str.substring(offset, i);
                offset = i + 1;
            }
        }
        if (substrIdx < size) {
            throw new IncorrectSizeException(size, substrIdx);
        }
        return substrings;
    }
}
