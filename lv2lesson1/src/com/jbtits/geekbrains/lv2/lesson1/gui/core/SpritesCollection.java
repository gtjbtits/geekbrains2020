package com.jbtits.geekbrains.lv2.lesson1.gui.core;

import com.jbtits.geekbrains.lv2.lesson1.gui.sprites.Sprite;

import java.util.Objects;

/**
 * @author Nikolay Zaytsev
 */
public class SpritesCollection {

    public static final int BASE_CONTAINER_SIZE = 10;
    private Sprite[] container;

    private int cursor;

    SpritesCollection(Sprite... sprites) {
        reCreateContainer(sprites);
    }

    public void add(Sprite sprite) {
        moveCursor();
        container[cursor++] = sprite;
    }

    public void remove(Sprite sprite) {
        int pos = getPosition(sprite);
        if (pos < 0) {
            throw new IllegalArgumentException("Sprite " + sprite + " isn't belongs to current collection");
        }
        if (Objects.nonNull(sprite)) {
            container[pos] = null;
            cursor = pos;
        }
    }

    private void moveCursor() {
        if (cursor < container.length && Objects.isNull(container[cursor])) {
            return;
        }
        for (int i = 0; i < container.length; i++) {
            if (Objects.isNull(container[i])) {
                cursor = i;
                return;
            }
        }
        reCreateContainer(this.container);
    }

    public Sprite[] toArray() {
        final Sprite[] sequence = new Sprite[container.length];
        int pos = 0;
        for (Sprite sprite: container) {
            if (Objects.nonNull(sprite)) {
                sequence[pos++] = sprite;
            }
        }
        if (pos < container.length) {
            final Sprite[] compactedSequence = new Sprite[pos];
            System.arraycopy(sequence, 0, compactedSequence, 0, pos);
            return compactedSequence;
        }
        return sequence;
    }

    private int getPosition(Sprite sprite) {
        for (int i = 0; i < container.length; i++) {
            if (container[i] == sprite) {
                return i;
            }
        }
        return -1;
    }

    private void reCreateContainer(Sprite[] source) {
        if (Objects.isNull(source)) {
            source = new Sprite[0];
        }
        final int length = source.length > 0
                ? source.length * 2
                : BASE_CONTAINER_SIZE;
        final Sprite[] newContainer = new Sprite[length];
        System.arraycopy(source, 0, newContainer, 0, source.length);
        this.cursor = source.length;
        this.container = newContainer;
    }
}
