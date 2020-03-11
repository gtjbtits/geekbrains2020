package com.jbtits.geekbrains.lv1.lesson2;

import java.util.Arrays;

/**
 * @author Nikolay Zaytsev
 */
public class ArraysMainer {

    public static void main(String[] args) {
        taskOne();
        taskTwo();
        taskTree();
        taskFour();
        taskFive();
        taskSix(new int[] {1, 1, 1, 2, 1});
        taskSix(new int[] {2, 1, 1, 2, 1});
        taskSix(new int[] {10, 1, 2, 3, 4});
        taskSeven(new int[] {1, 2, 3, 4, 5}, 10);
        taskSeven(new int[] {1, 2, 3, 4, 5}, -10);
        taskSeven(new int[] {1, 2, 3, 4, 5}, 1);
        taskSeven(new int[] {1, 2, 3, 4, 5}, -4);
        taskSevenCyclic(new int[] {1, 2, 3}, 11);
        taskSevenCyclic(new int[] {1, 2, 3}, -1);
        taskSevenCyclic(new int[] {}, 10);
        taskSevenCyclic(new int[] {1}, 10);
        taskSevenCyclic(new int[] {1, 2, 3, 4, 5, 6}, 0);
        taskSevenCyclic(new int[] {1, 2, 3, 4, 5, 6}, 1);
        taskSevenCyclic(new int[] {1, 2, 3, 4, 5, 6}, 2);
        taskSevenCyclic(new int[] {1, 2, 3, 4, 5, 6}, 3);
        taskSevenCyclic(new int[] {1, 2, 3, 4, 5, 6}, 4);
        taskSevenCyclic(new int[] {1, 2, 3, 4, 5, 6}, 5);
        taskSevenCyclic(new int[] {1, 2, 3, 4, 5, 6}, 6);
        taskSevenCyclic(new int[] {1, 2, 3, 4, 5, 6, 7}, 0);
        taskSevenCyclic(new int[] {1, 2, 3, 4, 5, 6, 7}, -1);
        taskSevenCyclic(new int[] {1, 2, 3, 4, 5, 6, 7}, -2);
        taskSevenCyclic(new int[] {1, 2, 3, 4, 5, 6, 7}, -3);
        taskSevenCyclic(new int[] {1, 2, 3, 4, 5, 6, 7}, -4);
        taskSevenCyclic(new int[] {1, 2, 3, 4, 5, 6, 7}, -5);
        taskSevenCyclic(new int[] {1, 2, 3, 4, 5, 6, 7}, -6);
        taskSevenCyclic(new int[] {1, 2, 3, 4, 5, 6, 7}, -7);
    }

    private static void taskSevenCyclic(int[] someArray, int offset) {
        cyclicMove(someArray, offset);
        printIntArray(someArray);
    }

    private static void taskSeven(int[] someArray, int offset) {
        move(someArray, offset);
        printIntArray(someArray);
    }

    private static void taskSix(int[] someArray) {
        System.out.printf("Has %s balance? %b", Arrays.toString(someArray), checkBalance(someArray));
        System.out.println();
    }

    private static void taskFive() {
        printIntMatrix(createDiagonalMatrix(7));
    }

    private static void taskFour() {
        final int[] someArray = {1, 3, -4, 7, 0};
        System.out.printf("Array %s. Max value is %d, min value is %d", Arrays.toString(someArray),
                maxValueOf(someArray), minValueOf(someArray));
        System.out.println();
    }

    private static void taskTree() {
        final int[] someArray = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        doubleElementsLessThanSix(someArray);
        printIntArray(someArray);
    }

    private static void taskTwo() {
        final int[] someArray = {0, 0, 0, 0, 0, 0, 0, 0};
        fillArrayWithCommonDifferenceOfTree(someArray);
        printIntArray(someArray);
    }

    private static void taskOne() {
        final int[] binaryArray = {0, 1, 0, 1, 1};
        invertBinaryArray(binaryArray);
        printIntArray(binaryArray);

    }

    private static void printIntArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    private static void printIntMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            printIntArray(row);
        }
    }

    private static void invertBinaryArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) {
                arr[i] = 0;
            } else if (arr[i] == 0) {
                arr[i] = 1;
            } else {
                throw new IllegalArgumentException("Not a binary array, cause element at pos " + i + " is " + arr[i]);
            }
        }
    }

    private static void fillArrayWithCommonDifferenceOfTree(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1 + 3 * i;
        }
    }

    private static void doubleElementsLessThanSix(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 6) {
                arr[i] *= 2;
            }
        }
    }

    private static int maxValueOf(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    private static int minValueOf(int[] arr) {
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }

    private static int[][] createDiagonalMatrix(int size) {
        final int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            matrix[i][i] = 1;
            matrix[i][(size - 1) - i] = 1;
        }
        return matrix;
    }

    private static boolean checkBalance(int[] arr) {
        if (arr.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }
        int leftSum = 0;
        for (int i = 0; i < arr.length; i++) {
            int rightSum = 0;
            for (int j = i + 1; j < arr.length; j++) {
                rightSum += arr[j];
            }
            leftSum += arr[i];
            if (leftSum == rightSum) {
                return true;
            }
        }
        return false;
    }

    private static void move(int[] arr, int offset) {
        int start;
        int direction;
        if (offset > 0) {
            direction = -1;
            start = arr.length - 2;
            offset = Math.min(offset, arr.length);
        } else {
            direction = 1;
            start = 1;
            offset = Math.max(offset, -arr.length);
        }
        for (int i = start; offset > 0 && i + offset >= 0
                || offset <= 0 && i + offset < arr.length; i += direction) {
            if (i >= 0 && i < arr.length
                    && (offset > 0 && i + offset < arr.length || offset <= 0 && i + offset >= 0)) {
                arr[i + offset] = arr[i];
            } else if (i < 0 || i >= arr.length) {
                arr[i + offset] = 0;
            }
        }
    }

    private static void cyclicMove(int[] arr, int offset) {
        if (arr.length == 0) {
            return;
        }
        offset = offset % arr.length;
        if (offset < 0) {
            offset = offset + arr.length;
        }
        for (int i = 0; i < offset; i++) {
            int currentValue = arr[0];
            for (int j = 0; j < arr.length; j++) {
                final int next = (j + 1) % arr.length;
                final int nextValue = arr[next];
                arr[next] = currentValue;
                currentValue = nextValue;
            }
        }
    }
}
