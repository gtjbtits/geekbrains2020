package com.jbtits.geekbrains.lv1.lesson5.domain;

import com.jbtits.geekbrains.lv1.lesson5.utils.DimensionUtils;
import com.jbtits.geekbrains.lv1.lesson5.utils.MathUtils;

/**
 * @author Nikolay Zaytsev
 */
public abstract class Animal {

    private static final String CAN_T = "can't";
    private int jumpHeightLimit;
    private int swimDistanceLimit;
    private int runDistanceLimit;

    private final int baseJumpHeightLimit;
    private final int baseSwimDistanceLimit;
    private final int baseRunDistanceLimit;

    public Animal(int baseJumpHeightLimit, int baseRunDistanceLimit) {
        this(baseJumpHeightLimit, baseRunDistanceLimit, -1);
    }

    public Animal(int baseJumpHeightLimit, int baseRunDistanceLimit, int baseSwimDistanceLimit) {
        this.baseJumpHeightLimit = jumpHeightLimit = baseJumpHeightLimit;
        this.baseRunDistanceLimit = runDistanceLimit = baseRunDistanceLimit;
        this.baseSwimDistanceLimit = swimDistanceLimit = baseSwimDistanceLimit;
    }

    public ActionResult run(int distanceInMeters) {
        return doAction(DimensionUtils.convertMetersToCentimeters(distanceInMeters), runDistanceLimit);
    }

    public ActionResult swim(int distanceInMeters) {
        return doAction(DimensionUtils.convertMetersToCentimeters(distanceInMeters), swimDistanceLimit);
    }

    public ActionResult jump(int heightInMeters) {
        return doAction(DimensionUtils.convertMetersToCentimeters(heightInMeters), jumpHeightLimit);
    }

    private ActionResult doAction(int value, int limit) {
        if (value < 0) {
            throw new IllegalArgumentException("Action value must be equals or grater than zero");
        }
        if (limit < 0) {
            return ActionResult.CANT_DO_IT;
        }
        if (value <= limit) {
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.FAIL;
        }
    }

    public void setJumpHeightLimit(int jumpHeightLimit) {
        this.jumpHeightLimit = jumpHeightLimit;
    }

    public void setSwimDistanceLimit(int swimDistanceLimit) {
        this.swimDistanceLimit = swimDistanceLimit;
    }

    public void setRunDistanceLimit(int runDistanceLimit) {
        this.runDistanceLimit = runDistanceLimit;
    }

    @Override
    public String toString() {
        final String jumpHeightLimitStr = (this.jumpHeightLimit >= 0)
                ? DimensionUtils.formattedMetersValueFromCentimeters(this.jumpHeightLimit)
                : CAN_T;
        final String swimDistanceLimitStr = (this.swimDistanceLimit >= 0)
                ? DimensionUtils.formattedMetersValueFromCentimeters(this.swimDistanceLimit)
                : CAN_T;
        final String runDistanceLimitStr = (this.baseRunDistanceLimit >= 0)
                ? DimensionUtils.formattedMetersValueFromCentimeters(runDistanceLimit)
                : CAN_T;
        return this.getClass().getSimpleName() + "{" +
                "jumpHeightLimit: " + jumpHeightLimitStr +
                ", swimDistanceLimit: " + swimDistanceLimitStr +
                ", runDistanceLimit: " + runDistanceLimitStr +
                '}';
    }

    public int getBaseJumpHeightLimit() {
        return baseJumpHeightLimit;
    }

    public int getBaseSwimDistanceLimit() {
        return baseSwimDistanceLimit;
    }

    public int getBaseRunDistanceLimit() {
        return baseRunDistanceLimit;
    }

    protected static class AnimalBuilder<A extends Animal> {

        private A animal;

        protected static <B extends Animal> AnimalBuilder<B> builder(B animal) {
            final AnimalBuilder<B> builder = new AnimalBuilder<>();
            builder.setAnimal(animal);
            return builder;
        }

        protected AnimalBuilder<A> setJumpHeightLimit(int jumpHeightLimitDeviation) {
            this.getAnimal().setJumpHeightLimit(MathUtils.randomizeLimit(this.getAnimal().getBaseJumpHeightLimit(),
                    jumpHeightLimitDeviation));
            return this;
        }

        protected AnimalBuilder<A> setRunDistanceLimit(int runDistanceLimitDeviation) {
            this.getAnimal().setRunDistanceLimit(MathUtils.randomizeLimit(this.getAnimal().getBaseRunDistanceLimit(),
                    runDistanceLimitDeviation));
            return this;
        }

        protected AnimalBuilder<A> setSwimDistanceLimit(int swimDistanceLimitDeviation) {
            this.getAnimal().setSwimDistanceLimit(MathUtils.randomizeLimit(this.getAnimal().getBaseSwimDistanceLimit(),
                    swimDistanceLimitDeviation));
            return this;
        }

        protected A build() {
            return this.getAnimal();
        }

        private void setAnimal(A animal) {
            this.animal = animal;
        }

        private A getAnimal() {
            return animal;
        }
    }

    public enum ActionResult {
        SUCCESS,
        FAIL,
        CANT_DO_IT;
    }
}
