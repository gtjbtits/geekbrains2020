package com.jbtits.geekbrains.lv2.lesson5;

import java.util.Arrays;
import java.util.Objects;

public class Main {

    private static final int SIZE = 1_000_000;

    public static void main(String[] args) {
        final float[] arr1 = initArray();
        System.out.printf("Single thread calculating execution time:\t\t%fs%n",
                toSeconds(calculateInSingleThread(arr1)));
        final float[] arr2 = initArray();
        System.out.printf("Parallel threads calculating execution time:\t%fs%n",
                toSeconds(calculateInParallelThreads(arr2)));
        System.out.printf("Is result the same in both cases?\t\t\t\t%b%n", Arrays.equals(arr1, arr2));
    }

    private static long calculateInParallelThreads(float[] arr2) {
        return measureExecutionTime(() -> {
                final int halfLength = arr2.length / 2;
                final float[][] parts = new float[2][halfLength];
                System.arraycopy(arr2, 0, parts[0], 0, halfLength);
                System.arraycopy(arr2, halfLength, parts[1], 0, halfLength);
                final Thread t1 = new Thread(() -> calculate(parts[0]));
                t1.start();
                final Thread t2 = new Thread(() -> calculate(parts[1], halfLength));
                t2.start();
                try {
                    t1.join();
                    t2.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
                System.arraycopy(parts[0], 0, arr2, 0, halfLength);
                System.arraycopy(parts[1], 0, arr2, halfLength, halfLength);
            });
    }

    private static long calculateInSingleThread(float[] arr) {
        return measureExecutionTime(() -> calculate(arr));
    }

    private static float[] initArray() {
        final float[] arr = new float[SIZE];
        Arrays.fill(arr, 1);
        return arr;
    }

    private static void calculate(float[] arr, int calculatingOffset) {
        if (Objects.isNull(arr) || calculatingOffset < 0) {
            throw new IllegalArgumentException("Array must be not null " +
                    "and calculatingOffset element must be equals or greater than zero");
        }
        for (int i = 0; i < arr.length; i++) {
            final int step = i + calculatingOffset;
            arr[i] = (float) (arr[i] * Math.sin(0.2f + step / 5) * Math.cos(0.2f + step / 5)
                    * Math.cos(0.4f + step / 2));
        }
    }

    private static void calculate(float[] arr) {
        calculate(arr, 0);
    }

    private static long measureExecutionTime(Runnable measuringCode) {
        if (Objects.isNull(measuringCode)) {
            throw new IllegalArgumentException("Measuring code must exists!");
        }
        final long a = System.currentTimeMillis();
        measuringCode.run();
        return System.currentTimeMillis() - a;
    }

    private static float toSeconds(long millis) {
        return millis * .0001f;
    }
}
