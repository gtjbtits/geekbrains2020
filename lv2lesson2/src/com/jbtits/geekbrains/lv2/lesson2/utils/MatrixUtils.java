package com.jbtits.geekbrains.lv2.lesson2.utils;

import com.jbtits.geekbrains.lv2.lesson2.domain.exceptions.IncorrectSizeException;

import java.util.Objects;

/**
 * @author Nikolay Zaytsev
 */
public class MatrixUtils {

    private MatrixUtils() {}

    /**
     * Десериализует строковое представление матрицы в двумерный массив заданного размера
     *
     * Если в процессе десериализации количество столбцов или строк привысит заданный размер size, то будет выброшено
     * исключение {@link IncorrectSizeException}
     *
     * @param matrixString Строковое представление матрицы
     * @param size Размер матрицы
     * @return Двумерный массив матрицы. Первый индекс - строки, второй - столбцы
     */
    public static String[][] deserialize(String matrixString, int size) {
        if (Objects.isNull(matrixString) || size < 1) {
            throw new IllegalArgumentException("Null matrix string or incorrect size");
        }
        final String[][] matrix = new String[size][];
        final String[] rows = BrandNewStringUtils.split(matrixString, size, '\n');
        for (int i = 0; i < rows.length; i++) {
            final String[] values = BrandNewStringUtils.split(rows[i], size, ' ');
            matrix[i] = values;
        }
        return matrix;
    }

    /**
     * Конвертирует строковую матрицу в целочисленную.
     *
     * Если хотя бы одно поле строковой матрицы не представляет собой число, то будет выброшено исключение
     * {@link NumberFormatException}
     *
     * @param source Исходная строковая матрица
     * @param size Размер исходной и результирующей матрицы
     * @return Целочисленную матрицу рамером size при успешной конвертации
     */
    public static int[][] convertToIntMatrix(String[][] source, int size) {
        if (Objects.isNull(source) || size < 1) {
            throw new IllegalArgumentException("Null source matrix or incorrect size");
        }
        final int[][] target = new int[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                target[y][x] = Integer.parseInt(source[y][x]);
            }
        }
        return target;
    }

    public static int sum(int[][] matrix, int size) {
        int sum = 0;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                sum += matrix[y][x];
            }
        }
        return sum;
    }

    public static int halfSum(int[][] matrix, int size) {
        return MatrixUtils.sum(matrix, size) / 2;
    }
}
