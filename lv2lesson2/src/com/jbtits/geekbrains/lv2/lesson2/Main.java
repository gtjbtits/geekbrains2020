package com.jbtits.geekbrains.lv2.lesson2;

import com.jbtits.geekbrains.lv2.lesson2.utils.MatrixUtils;

import java.util.Arrays;

public class Main {

    public static final int MATRIX_SIZE = 4;

    public static void main(String[] args) {
        final String serializedMatrix = "10 3 1 2\n2 3 2 2\n5 6 6 1\n300 3 1 0";
        final String[][] strMatrix;
        try {
            strMatrix = MatrixUtils.deserialize(serializedMatrix, MATRIX_SIZE);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Matrix deserialization failed with message: " + e.getMessage(), e);
        }
        System.out.println(Arrays.deepToString(strMatrix));
        final int[][] intMatrix;
        try {
            intMatrix = MatrixUtils.convertToIntMatrix(strMatrix, MATRIX_SIZE);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Matrix convertation failed with message: " + e.getMessage(), e);
        }
        System.out.println(MatrixUtils.halfSum(intMatrix, MATRIX_SIZE));
    }
}
