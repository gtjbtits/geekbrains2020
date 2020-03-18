package com.jbtits.geekbrains.lv1.lesson5.utils;

/**
 * @author Nikolay Zaytsev
 */
public class DimensionUtils {

    private DimensionUtils() {}

    public static final int CENTIMETERS_IN_METER = 100;

    public static int convertMetersToCentimeters(int meters) {
        return meters * CENTIMETERS_IN_METER;
    }

    public static float convertCentimetersToMeters(int centimeters) {
        return (float) centimeters / CENTIMETERS_IN_METER;
    }

    public static String formattedMetersValueFromCentimeters(int centimeters) {
        return String.format("%.2f m", convertCentimetersToMeters(centimeters));
    }
}
