package com.jbtits.gb.lv3.lesson5.stages;

import com.jbtits.gb.lv3.lesson5.Car;
import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;

/**
 * @author Nikolay Zaytsev
 * @since 01.08.20
 */
public class Tunnel extends Stage {

    private final Semaphore capacitySemaphore;

    public Tunnel(int length, int partiesCount) {
        this.capacitySemaphore = new Semaphore(partiesCount / 2, true);
        this.description = "Тоннель " + length + " метров";
        this.length = length;
    }

    @Override
    @SneakyThrows
    public void go(Car c) {
        if (capacitySemaphore.availablePermits() < 1) {
            System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
        }
        capacitySemaphore.acquire();
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
        } finally {
            capacitySemaphore.release();
        }
    }
}
