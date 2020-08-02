package com.jbtits.gb.lv3.lesson5;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Nikolay Zaytsev
 * @since 01.08.20
 */
public class Car implements Runnable {
    private static final AtomicInteger CARS_COUNTER = new AtomicInteger();
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        this.name = "Участник #" + CARS_COUNTER.incrementAndGet();
    }
    @Override
    public void run() {
        getReady();
        this.race.readyToRace();
        goGoGo();
        this.race.finish(this);
    }

    private void goGoGo() {
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
    }

    private void getReady() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
