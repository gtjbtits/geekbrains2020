package com.jbtits.gb.lv3.lesson6;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayUtilsTest {

    @Test
    public void copyAfterLastFourMustReturnCorrectArrayPart() {
        final int[][] testData = {
                {1, 2, 4, 4, 2, 3, 4, 1, 7},
                {4},
                {4, 4, 3, -2},
                {1, 4, 1}
        };
        final int[][] answers = {
                {1, 7},
                {},
                {3, -2},
                {1}
        };
        for (int i = 0; i < testData.length; i++) {
            assertArrayEquals(answers[i], ArrayUtils.copyAfterLastFour(testData[i]));
        }
    }

    @Test
    public void copyAfterLastFourMustThrowExceptionWhenArrayIsNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> ArrayUtils.copyAfterLastFour(null));
        assertThrows(IllegalArgumentException.class, () -> ArrayUtils.copyAfterLastFour(new int[] {}));
    }

    @Test
    public void copyAfterLastFourMustThrowExceptionWhenArrayNotContainsAnyFour() {
        final int[][] testData = {
                {1, 2, 3},
                {-7},
                {0, 0, 0, 0}
        };
        for (int[] testArray: testData) {
            assertThrows(IllegalArgumentException.class, () -> ArrayUtils.copyAfterLastFour(testArray));
        }
    }

    @Test
    public void hasOneAndFourMustReturnTrueWhenArrayContainsAtLeastOneOneAndOneFour() {
        final int[][] testData = {
                {1, 3, 4, 0, 100},
                {4, 1, 4, 1, 4, 1},
                {-4, -5, 3, 4, 0, -7, 1, 2},
                {4, 1}
        };
        for (int[] testArray: testData) {
            assertTrue(ArrayUtils.hasOneAndFour(testArray));
        }
    }

    @Test
    public void hasOneAndFourMustReturnFalseWhenArrayNotContainsAtLeastOneOneAndOneFour() {
        final int[][] testData = {
                {1, 3, -4, 0, 100},
                {4, 0, 4, 0, 4, 0},
                {},
                {1},
                {-4, -5, 3, 0, 0, -7, 1, 2},
                {4, -1}
        };
        for (int[] testArray: testData) {
            assertFalse(ArrayUtils.hasOneAndFour(testArray));
        }
    }

    @Test
    public void hasOneAndFourMustThrowExceptionWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> ArrayUtils.hasOneAndFour(null));
    }

}
