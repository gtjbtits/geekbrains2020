package com.jbtits.geekbrains.lv1.lesson5.domain;

import com.jbtits.geekbrains.lv1.lesson5.utils.DimensionUtils;

/**
 * @author Nikolay Zaytsev
 */
public class Bird extends Animal {

    private Bird() {
        super(20, 5 * DimensionUtils.CENTIMETERS_IN_METER);
    }

    public static class BirdBuilder extends AnimalBuilder<Bird> {

        public static Bird buildOne() {
            return AnimalBuilder.builder(new Bird())
                    .setJumpHeightLimit(3)
                    .setRunDistanceLimit(DimensionUtils.CENTIMETERS_IN_METER)
                    .build();
        }
    }
}
