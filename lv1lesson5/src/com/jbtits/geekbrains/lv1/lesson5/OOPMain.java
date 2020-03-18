package com.jbtits.geekbrains.lv1.lesson5;

import com.jbtits.geekbrains.lv1.lesson5.domain.*;

/**
 * @author Nikolay Zaytsev
 */
public class OOPMain {

    public static void main(String[] args) {
        runTest(100);
        jumpTest(1);
        swimTest(20);
    }

    private static void swimTest(int distanceInMeters) {
        System.out.format("Swim %d meters test begins!", distanceInMeters);
        System.out.println();
        for (Animal animal: generateZoo()) {
            System.out.printf("Animal type: %s, distance: %d, result: %s", animal.getClass().getSimpleName(), distanceInMeters,
                    animal.swim(distanceInMeters));
            System.out.println();
        }
        System.out.println();
    }

    private static void jumpTest(int heightInMeters) {
        System.out.printf("Jump %d meters height test begins!", heightInMeters);
        System.out.println();
        for (Animal animal: generateZoo()) {
            System.out.printf("Animal type: %s, height: %d, result: %s", animal.getClass().getSimpleName(), heightInMeters,
                    animal.jump(heightInMeters));
            System.out.println();
        }
        System.out.println();
    }

    private static void runTest(int distanceInMeters) {
        System.out.format("Run %d meters test begins!", distanceInMeters);
        System.out.println();
        for (Animal animal: generateZoo()) {
            System.out.printf("Animal type: %s, distance: %d, result: %s", animal.getClass().getSimpleName(), distanceInMeters,
                    animal.run(distanceInMeters));
            System.out.println();
        }
        System.out.println();
    }

    private static Animal[] generateZoo() {
        final Animal[] animals = new Animal[] {
                Bird.BirdBuilder.buildOne(),
                Cat.CatBuilder.buildOne(),
                Dog.DogBuilder.buildOne(),
                Horse.HorseBuilder.buildOne()
        };
        System.out.println("New Zoo has been generated. Introducing the animals...");
        for (Animal animal: animals) {
            System.out.println(animal);
        }
        return animals;
    }
}
