package com.jbtits.gb.lv3.lesson5.stages;

import com.jbtits.gb.lv3.lesson5.Car;

/**
 * @author Nikolay Zaytsev
 * @since 01.08.20
 */
public abstract class Stage {
    protected int length;
    protected String description;
    public abstract void go(Car c);
}
