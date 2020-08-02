package com.jbtits.gb.lv3.lesson5.stages;

import com.jbtits.gb.lv3.lesson5.Car;

/**
 * @author Nikolay Zaytsev
 * @since 01.08.20
 */
public class Road extends Stage {
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }
    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}