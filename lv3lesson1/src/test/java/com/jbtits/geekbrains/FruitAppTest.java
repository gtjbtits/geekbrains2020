package com.jbtits.geekbrains;

import static com.jbtits.geekbrains.domain.Apple.APPLE_WEIGHT;
import static com.jbtits.geekbrains.domain.Orange.ORANGE_WEIGHT;
import static org.junit.Assert.*;

import com.jbtits.geekbrains.domain.Apple;
import com.jbtits.geekbrains.domain.FruitBox;
import com.jbtits.geekbrains.domain.Orange;
import org.junit.Test;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class FruitAppTest {

    @Test
    public void fruitsMustHaveCorrectWeights() {
        assertEquals(APPLE_WEIGHT, new Apple().getWeight(), 0);
        assertEquals(ORANGE_WEIGHT, new Orange().getWeight(), 0);
    }

    @Test
    public void fruitBoxesMustHaveCorrectWeights() {
        final FruitBox<Apple> appleBox = new FruitBox<>();
        final FruitBox<Orange> orangeBox = new FruitBox<>();
        appleBox.putAll(List.of(new Apple(), new Apple()));
        orangeBox.putAll(List.of(new Orange(), new Orange()));

        assertEquals(APPLE_WEIGHT * 2, appleBox.getWeight(), 0);
        assertEquals(ORANGE_WEIGHT * 2, orangeBox.getWeight(), 0);
        assertFalse(appleBox.compare(orangeBox));

        appleBox.put(new Apple());

        assertTrue(appleBox.compare(orangeBox));
    }

    @Test
    public void fruitBoxMustCorrectEmpty() {
        final FruitBox<Apple> appleBox1 = new FruitBox<>();
        final FruitBox<Apple> appleBox2 = new FruitBox<>();
        appleBox1.putAll(List.of(new Apple(), new Apple(), new Apple()));
        appleBox2.put(new Apple());
        assertEquals(3, appleBox1.getCount());
        assertEquals(1, appleBox2.getCount());
        appleBox1.empty(appleBox2);
        assertEquals(0, appleBox1.getCount());
        assertEquals(4, appleBox2.getCount());
    }
}
