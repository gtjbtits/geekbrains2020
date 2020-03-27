package com.jbtits.geekbrains.lv1.lesson7.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Random;

/**
 * @author Nikolay Zaytsev
 */
public class TicTacToeWindow extends JFrame {

    private static final Random RANDOM = new Random();

    private static final int FRAME_HEIGHT = 400;
    private static final int FRAME_WIDTH = 400;
    private static final int LOCATION_X = 0;
    private static final int LOCATION_Y = 0;

    private static final char CELL_EMPTY = ' ';
    private static final char CELL_HUMAN = 'O';
    private static final char CELL_AI = 'X';

    private int fieldSizeX;
    private int fieldSizeY;
    private char[][] field;
    private JPanel visibleField;

    public TicTacToeWindow() {
        this.setVisible(true);
        this.setResizable(false);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setLocation(LOCATION_X, LOCATION_Y);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        prepareControls();
    }

    public void startNewGame(int fieldSizeX, int fieldSizeY) {
        initField(fieldSizeX, fieldSizeY);
        if (Objects.nonNull(visibleField)) {
            this.remove(visibleField);
        }
        visibleField = new GameField();
        this.add(visibleField, BorderLayout.CENTER);
        this.validate();
    }

    private void imLucky() {
        final int randomSizeX = RANDOM.nextInt(3) + 3;
        final int randomSizeY = RANDOM.nextInt(3) + 3;
        this.startNewGame(randomSizeX, randomSizeY);
    }

    private void initField(int fieldSizeX, int fieldSizeY) {
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.field = new char[this.fieldSizeY][this.fieldSizeX];
        for (int i = 0; i < this.fieldSizeY; i++) {
            for (int j = 0; j < this.fieldSizeX; j++) {
                field[i][j] = CELL_EMPTY;
            }
        }
    }

    private void prepareControls() {
        this.add(new Controls(this), BorderLayout.SOUTH);
    }

    private boolean isValidCell(int x, int y) {
        return field[y][x] == CELL_EMPTY;
    }

    private boolean acceptCoordinates(int x, int y) {
        if (!isValidCell(x, y)) {
            return false;
        }
        field[y][x] = CELL_HUMAN;
        return true;
    }

    private static class Controls extends JPanel {

        private Controls(TicTacToeWindow parentWindow) {
            final JButton newGameBtn = new JButton("I'm lucky!");
            newGameBtn.addActionListener(e -> parentWindow.imLucky());
            this.add(newGameBtn);
        }

    }

    private class GameField extends JPanel {

        private GameField() {
            init();
        }

        private void init() {
            this.setLayout(new GridLayout(fieldSizeY, fieldSizeX));
            for (int i = 0; i < fieldSizeY; i++) {
                for (int j = 0; j < fieldSizeX; j++) {
                    final int y = i;
                    final int x = j;
                    final Cell cell = convertToCell(field[y][x]);
                    final ActionListener listener = e -> {
                        if (acceptCoordinates(x, y)) {
                            this.removeAll();
                            this.init();
                            this.validate();
                        }
                    };
                    if (field[y][x] == CELL_EMPTY) {
                        cell.addActionListener(listener);
                    }
                    this.add(cell);
                }
            }
        }

        private Cell convertToCell(char cellChar) {
            if (cellChar == CELL_EMPTY) {
                return new Cell();
            } else if (cellChar == CELL_AI) {
                return new AiCell();
            } else if (cellChar == CELL_HUMAN) {
                return new HumanCell();
            } else {
                throw new IllegalArgumentException("Unknown cell char");
            }
        }

        private class Cell extends JButton {
            private Cell() {
                this.setText(new String(new char[] {CELL_EMPTY}));
            }
        }

        private class HumanCell extends Cell {
            private HumanCell() {
                this.setText(new String(new char[] {CELL_HUMAN}));
            }
        }

        private class AiCell extends Cell {
            private AiCell() {
                this.setText(new String(new char[] {CELL_AI}));
            }
        }
    }
}
