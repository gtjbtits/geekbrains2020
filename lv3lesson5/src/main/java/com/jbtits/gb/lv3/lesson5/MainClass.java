package com.jbtits.gb.lv3.lesson5;

import com.jbtits.gb.lv3.lesson5.stages.Road;
import com.jbtits.gb.lv3.lesson5.stages.Tunnel;

/**
 * @author Nikolay Zaytsev
 * @since 01.08.20
 */
public class MainClass {

    public static final int PARTIES_COUNT = 4;

    public static void main(String[] args) {
        Race race = new Race(PARTIES_COUNT,
                new Tunnel(20, PARTIES_COUNT),
                new Road(60),
                new Tunnel(80, PARTIES_COUNT),
                new Road(40));
        race.start();
    }
}
