package com.jbtits.geekbrains.lv1.lesson5.domain;

import com.jbtits.geekbrains.lv1.lesson5.utils.DimensionUtils;

/**
 * @author Nikolay Zaytsev
 */
public class Dog extends Animal {

    private Dog() {
        super(50, 500 * DimensionUtils.CENTIMETERS_IN_METER,
                10 * DimensionUtils.CENTIMETERS_IN_METER);
    }

    public static class DogBuilder extends AnimalBuilder<Dog> {

        public static Dog buildOne() {
            return AnimalBuilder.builder(new Dog())
                    .setJumpHeightLimit(10)
                    .setRunDistanceLimit(100 * DimensionUtils.CENTIMETERS_IN_METER)
                    .setSwimDistanceLimit(DimensionUtils.CENTIMETERS_IN_METER)
                    .build();
        }
    }
}
