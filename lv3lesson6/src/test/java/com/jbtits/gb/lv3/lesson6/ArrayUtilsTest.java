package com.jbtits.gb.lv3.lesson6;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void copyAfterLastFourMustThrowExceptionThenArrayIsNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> ArrayUtils.copyAfterLastFour(null));
        assertThrows(IllegalArgumentException.class, () -> ArrayUtils.copyAfterLastFour(new int[] {}));
    }

    @Test
    public void copyAfterLastFourMustThrowExceptionThenArrayNotContainsAnyFour() {
        final int[][] testData = {
                {1, 2, 3},
                {-7},
                {0, 0, 0, 0}
        };
        for (int[] testArray: testData) {
            assertThrows(IllegalArgumentException.class, () -> ArrayUtils.copyAfterLastFour(testArray));
        }
    }

}
