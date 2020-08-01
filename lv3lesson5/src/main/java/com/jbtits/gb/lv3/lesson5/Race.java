package com.jbtits.gb.lv3.lesson5;

import com.jbtits.gb.lv3.lesson5.stages.Stage;
import lombok.Builder;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Nikolay Zaytsev
 * @since 01.08.20
 */
public class Race {

    private final ArrayList<Stage> stages;
    private final CyclicBarrier startLine;
    private final CountDownLatch finishLine;
    private final Car[] parties;
    private Car winner;

    public Race(int partiesCount, Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
        this.startLine = new CyclicBarrier(partiesCount,
                () -> System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!"));
        this.finishLine = new CountDownLatch(partiesCount);
        this.parties = new Car[partiesCount];
    }

    @SneakyThrows
    public void start() {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        for (int i = 0; i < this.parties.length; i++) {
            this.parties[i] = new Car(this, 20 + (int) (Math.random() * 10));
            new Thread(parties[i]).start();
        }
        this.finishLine.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }

    @SneakyThrows
    public void readyToRace() {
        this.startLine.await();
    }

    @SneakyThrows
    public synchronized void finish(Car car) {
        if (this.winner == null) {
            this.winner = car;
            System.out.println(car.getName() + " - WIN");
        }
        this.finishLine.countDown();
    }

    public List<Stage> getStages() {
        return stages;
    }
}
