package com.jbtits.geekbrains.lv1.lesson5.utils;

import java.util.Random;

/**
 * @author Nikolay Zaytsev
 */
public class MathUtils {

    private MathUtils() {}

    private static final Random RANDOM = new Random();

    public static int randomizeLimit(int base, int deviation) {
        return base + RANDOM.nextInt(deviation * 2 + 1) - deviation;
    }
}
