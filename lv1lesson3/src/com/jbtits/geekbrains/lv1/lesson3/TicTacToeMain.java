package com.jbtits.geekbrains.lv1.lesson3;

import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Nikolay Zaytsev
 */
public class TicTacToeMain {

    private static final char CELL_X = 'X';
    private static final char CELL_O = 'O';
    private static final char CELL_EMPTY = '.';

    private static final char HUMAN_SIGN = CELL_X;
    private static final char AI_SIGN = CELL_O;

    private static final int WIN_SEQUENCE_COUNT = 4;
    private static final int FIELD_SIZE_X = 5;
    private static final int FIELD_SIZE_Y = 5;
    private static final char[][] field = new char[FIELD_SIZE_Y][FIELD_SIZE_X];

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {
        init();
        while (true) {
            turn(TicTacToeMain::humanInput, HUMAN_SIGN);
            printField();
            if (checkWinConditionsForPlayer(HUMAN_SIGN)) {
                System.out.println("Human wins!");
                break;
            }
            if (checkDrawConditions()) {
                System.out.println("Draw..");
                break;
            }
            turn(TicTacToeMain::aiInput, AI_SIGN);
            printField();
            if (checkWinConditionsForPlayer(AI_SIGN)) {
                System.out.println("AI wins!");
                break;
            }
            if (checkDrawConditions()) {
                System.out.println("Draw..");
                break;
            }
        }
    }

    private static void init() {
        for (int i = 0; i < FIELD_SIZE_Y; i++) {
            for (int j = 0; j < FIELD_SIZE_X; j++) {
                field[i][j] = CELL_EMPTY;
            }
        }
    }

    private static boolean checkDrawConditions() {
        for (int i = 0; i < FIELD_SIZE_Y; i++) {
            for (int j = 0; j < FIELD_SIZE_X; j++) {
                if (field[i][j] == CELL_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkWinConditionsForPlayer(char cellSign) {
        return checkfieldForAnyHorizontalWinSequence(cellSign)
                || checkFieldForAnyVerticalWinSequence(cellSign)
                || checkFieldForAnyDigonalWinSequence(cellSign);
    }

    private static Optional<int[]> searchForWinMove(char cellSign) {
        for (int y = 0; y < FIELD_SIZE_Y; y++) {
            for (int x = 0; x < FIELD_SIZE_X; x++) {
                if (isInvalidCell(x, y)) {
                    continue;
                }
                field[y][x] = cellSign;
                boolean isWinOccurs = checkWinConditionsForPlayer(cellSign);
                field[y][x] = CELL_EMPTY;
                if (isWinOccurs) {
                    return Optional.of(new int[] {x, y});
                }
            }
        }
        return Optional.empty();
    }


    private static boolean checkfieldForAnyHorizontalWinSequence(char cellSign) {

        for (int y = 0; y < FIELD_SIZE_Y; y++) {
            int toGo = WIN_SEQUENCE_COUNT;
            for (int x = 0; x < FIELD_SIZE_X; x++) {
                if (field[y][x] == cellSign) {
                    toGo--;
                } else {
                    toGo = WIN_SEQUENCE_COUNT;
                }
                if (toGo == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkFieldForAnyVerticalWinSequence(char cellSign) {
        for (int x = 0; x < FIELD_SIZE_X; x++) {
            int toGo = WIN_SEQUENCE_COUNT;
            for (int y = 0; y < FIELD_SIZE_Y; y++) {
                if (field[y][x] == cellSign) {
                    toGo--;
                } else {
                    toGo = WIN_SEQUENCE_COUNT;
                }
                if (toGo == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkFieldForAnyDigonalWinSequence(char cellSign) {
        for (int y = 0; y < FIELD_SIZE_Y; y++) {
            for (int x = 0; x < FIELD_SIZE_X; x++) {
                if (field[y][x] != cellSign) {
                    continue;
                }
                int toGo = WIN_SEQUENCE_COUNT;
                for (int yy = y, xx = x; yy < FIELD_SIZE_Y && xx < FIELD_SIZE_X; yy++, xx++) {
                    if (field[yy][xx] == cellSign) {
                        toGo--;
                    } else {
                        break;
                    }
                    if (toGo == 0) {
                        return true;
                    }
                }
                toGo = WIN_SEQUENCE_COUNT;
                for (int yy = y, xx = x; yy < FIELD_SIZE_Y && xx >= 0; yy++, xx--) {
                    if (field[yy][xx] == cellSign) {
                        toGo--;
                    } else {
                        break;
                    }
                    if (toGo == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isInvalidCell(int x, int y) {
        return x < 0 || x >= FIELD_SIZE_X || y < 0 || y >= FIELD_SIZE_Y || field[y][x] != CELL_EMPTY;
    }

    /**
     * Универсальная процедура выполнения хода игрока
     *
     * @param cellCoordsSupplier Функция, передающая координаты ячейки, выбранной игроком при ходе. Возвращает массив
     *                           int[], где int[0] - координата X ячейки, а int[1] - координата Y ячейки
     * @param cellSign Символ игрока. Может быть {@link TicTacToeMain#CELL_X} или
     *             {@link TicTacToeMain#CELL_O}
     */
    private static void turn(Supplier<int[]> cellCoordsSupplier, char cellSign) {
        int x;
        int y;
        do {
            final int[] coordinates = cellCoordsSupplier.get();
            x = coordinates[0];
            y = coordinates[1];
        } while (isInvalidCell(x, y));
        field[y][x] = cellSign;
    }

    private static int[] humanInput() {
        System.out.println("Please input space separated cell coordinates to input sign '" + HUMAN_SIGN + "'");
        System.out.printf("X range [1 to %d]", FIELD_SIZE_X);
        System.out.println();
        System.out.printf("Y range [1 to %d]", FIELD_SIZE_Y);
        System.out.println();
        System.out.println("Point [1, 1] is an upper left corner");
        System.out.println("X Y >>>");
        final int x = scanner.nextInt() - 1;
        final int y = scanner.nextInt() - 1;
        return new int[] {x, y};
    }

    private static int[] aiRandomInput() {
        final int x = random.nextInt(FIELD_SIZE_X);
        final int y = random.nextInt(FIELD_SIZE_Y);
        return new int[] {x, y};
    }

    private static int[] aiInput() {
        return Stream.of(
                searchForWinMove(AI_SIGN),
                searchForWinMove(HUMAN_SIGN)
        ).filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseGet(TicTacToeMain::aiRandomInput);
    }

    private static void printField() {
        for (int i = 0; i < FIELD_SIZE_Y; i++) {
            for (int j = 0; j < FIELD_SIZE_X; j++) {
                System.out.print(field[i][j] + "|");
            }
            System.out.println();
        }
        for (int i = 0; i < FIELD_SIZE_X * 2; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}