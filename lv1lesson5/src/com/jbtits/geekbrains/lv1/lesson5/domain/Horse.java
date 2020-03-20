package com.jbtits.geekbrains.lv1.lesson5.domain;

import com.jbtits.geekbrains.lv1.lesson5.utils.DimensionUtils;

/**
 * @author Nikolay Zaytsev
 */
public class Horse extends Animal {

    private Horse() {
        super(3 * DimensionUtils.CENTIMETERS_IN_METER,
                1_500 * DimensionUtils.CENTIMETERS_IN_METER,
                100 * DimensionUtils.CENTIMETERS_IN_METER);
    }

    public static class HorseBuilder extends AnimalBuilder<Horse> {

        public static Horse buildOne() {
            return AnimalBuilder.builder(new Horse())
                    .setJumpHeightLimit(50)
                    .setRunDistanceLimit(500 * DimensionUtils.CENTIMETERS_IN_METER)
                    .setSwimDistanceLimit(10 * DimensionUtils.CENTIMETERS_IN_METER)
                    .build();
        }
    }
}
