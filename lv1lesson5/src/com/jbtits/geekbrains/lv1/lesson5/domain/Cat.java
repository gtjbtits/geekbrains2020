package com.jbtits.geekbrains.lv1.lesson5.domain;

import com.jbtits.geekbrains.lv1.lesson5.utils.DimensionUtils;

/**
 * @author Nikolay Zaytsev
 */
public class Cat extends Animal {

    private Cat() {
        super(2 * DimensionUtils.CENTIMETERS_IN_METER,
                200 * DimensionUtils.CENTIMETERS_IN_METER);
    }

    public static class CatBuilder extends AnimalBuilder<Cat> {

        public static Cat buildOne() {
            return AnimalBuilder.builder(new Cat())
                    .setJumpHeightLimit(50)
                    .setRunDistanceLimit(50 * DimensionUtils.CENTIMETERS_IN_METER)
                    .build();
        }
    }
}
