package com.jbtits.gb.lv3.lesson6;

import java.util.Arrays;

public class ArrayUtils {

    private ArrayUtils() {}

    public static int[] copyAfterLastFour(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array can't be null or empty");
        }
        int copyStart = -1;
        boolean jFoundFour = false;
        for (int i = 0, j = arr.length - 1; i <= j; i++, j--) {
            if (arr[j] == 4) {
                copyStart = j;
                jFoundFour = true;
            }
            if (!jFoundFour && arr[i] == 4) {
                copyStart = i;
            }
        }
        if (copyStart < 0) {
            throw new IllegalArgumentException("Array " + Arrays.toString(arr) + " not contains any '4'");
        }
        final int copyLength = arr.length - copyStart - 1;
        if (copyLength == 0) {
            return new int[] {};
        }
        final int[] part = new int[copyLength];
        System.arraycopy(arr, copyStart + 1, part, 0, copyLength);
        return part;
    }
}
