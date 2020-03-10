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
        taskSeven(new int[] {1, 2, 3, 4, 5}, 3);
        taskSeven(new int[] {1, 2, 3, 4, 5}, -3);
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
        printIntMatrix(createIdentityMatrix(2));
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
            switch (arr[i]) {
                case 1:
                    arr[i] = 0;
                    break;
                case 0:
                    arr[i] = 1;
                    break;
                default:
                    throw new IllegalArgumentException("Not a binary array, cause element at pos " + i + " is "
                            + arr[i]);
            }
        }
    }

    private static void fillArrayWithCommonDifferenceOfTree(int[] arr) {
        for (int pos = 0, val = 1; pos < arr.length; pos++, val += 3) {
            arr[pos] = val;
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

    private static int[][] createIdentityMatrix(int size) {
        final int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            matrix[i][i] = 1;
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
        if (offset >= 0) {
            moveRight(arr, offset);
        } else {
            moveLeft(arr, -offset);
        }
    }

    private static void moveRight(int[] arr, int offset) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset must be equals or greater than zero");
        }
        int start = arr.length - 2;
        if (offset > arr.length) {
            offset = arr.length;
            start = -1;
        }
        for (int i = start; i + offset >= 0; i--) {
            if (i >= 0 && i + offset < arr.length) {
                arr[i + offset] = arr[i];
            } else if (i < 0) {
                arr[i + offset] = 0;
            }
        }
    }

    private static void moveLeft(int[] arr, int offset) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset must be equals or greater than zero");
        }
        int start = 1;
        if (offset > arr.length) {
            offset = start = arr.length;
        }
        for (int i = start; i < arr.length + offset; i++) {
            if (i < arr.length && i - offset >= 0) {
                arr[i - offset] = arr[i];
            } else if (i >= arr.length) {
                arr[i - offset] = 0;
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
