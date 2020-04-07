package com.jbtits.geekbrains.lv2.lesson1.utils;

import java.awt.*;
import java.util.Random;

/**
 * @author Nikolay Zaytsev
 */
public class Randomizer {

    private static final int MAX_COLOR_INTENSITY = 0xFF;

    private static final Random RANDOM = new Random();

    private Randomizer() {

    }

    public static int getRandomIntGreaterThanZero(int bound) {
        return Randomizer.getRandomInt(bound, bound / 3);
    }

    public static int getRandomInt(int bound) {
        return Randomizer.getRandomInt(bound, 0);
    }

    private static int getRandomInt(int bound, int base) {
        return RANDOM.nextInt(bound) + base;
    }

    public static Color getRandomColor() {
        return new Color(
                Randomizer.getRandomColorIntensity(),
                Randomizer.getRandomColorIntensity(),
                Randomizer.getRandomColorIntensity()
        );
    }

    private static int getRandomColorIntensity() {
        return Randomizer.getRandomInt(MAX_COLOR_INTENSITY);
    }
}
