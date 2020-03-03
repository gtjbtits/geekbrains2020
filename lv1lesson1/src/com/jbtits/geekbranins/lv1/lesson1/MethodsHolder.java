package com.jbtits.geekbranins.lv1.lesson1;

/**
 * @author Nikolay Zaytsev
 */
public class MethodsHolder {

    public static void main(String[] args) {
        printFormattedLine("Calculate result: %.2f", calculate(1, 2, 3, 4));
        printFormattedLine("7+5 is in (10, 20]? %b", isSumBetweenTenAndTwenty(7, 5));
        printFormattedLine("Is %d a positive number? %b", -100, checkNumberPositivity(-100));
        System.out.println(generateGreetingsFor("Дорогой друг"));
        printFormattedLine("Is %d a leap year? %b", 1, isLeapYear(1));
        printFormattedLine("Is %d a leap year? %b", 4, isLeapYear(4));
        printFormattedLine("Is %d a leap year? %b", 100, isLeapYear(100));
        printFormattedLine("Is %d a leap year? %b", 400, isLeapYear(400));
        printFormattedLine("Is %d a leap year? %b", 500, isLeapYear(500));
        printFormattedLine("Is %d a leap year? %b", 800, isLeapYear(800));
    }

    private static boolean isLeapYear(int year) {
        return year % 400 == 0 || (year % 4 == 0 && year % 100 > 0);
    }


    private static String generateGreetingsFor(String name) {
        return "Привет, " + name + "!";
    }

    private static boolean checkNumberPositivity(int number) {
        return number >= 0;
    }

    private static float calculate(int a, int b, int c, int d) {
        return a * (b + (float) c / d);
    }

    private static boolean isSumBetweenTenAndTwenty(int a, int b) {
        final int sum = a + b;
        return sum > 10 && sum <= 20;
    }

    private static void printFormattedLine(String format, Object ... args) {
        System.out.printf(format, args);
        System.out.println();
    }
}
