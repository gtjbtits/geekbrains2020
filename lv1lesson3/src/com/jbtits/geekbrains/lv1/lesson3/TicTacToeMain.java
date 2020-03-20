package com.jbtits.geekbrains.lv1.lesson3;

import java.util.Random;
import java.util.Scanner;
import java.util.function.*;

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
        for (int i = 0; i < FIELD_SIZE_Y; i++) {
            for (int j = 0; j < FIELD_SIZE_X; j++) {
                if (searchForSequenceFromPoint(j, i, 1, 0, WIN_SEQUENCE_COUNT, cellSign) == 0
                        || searchForSequenceFromPoint(j, i, 0, 1, WIN_SEQUENCE_COUNT, cellSign) == 0
                        || searchForSequenceFromPoint(j, i, 1, 1, WIN_SEQUENCE_COUNT, cellSign) == 0
                        || searchForSequenceFromPoint(j, i, -1, 1, WIN_SEQUENCE_COUNT, cellSign) == 0
                ) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int searchForSequenceFromPoint(int x, int y, int stepX, int stepY, int length, char cellSign) {
        if (field[y][x] != cellSign
                || (stepX == 0 && stepY == 0)
                || (stepX < 0 && x - (length - 1) < 0)
                || (stepX > 0 && x + length > FIELD_SIZE_X)
                || (stepY < 0 && y - (length - 1) < 0)
                || (stepY > 0 && y + length > FIELD_SIZE_Y)) {
            return length;
        }
        int toGo = length - 1;
        for (int i = y + stepY, j = x + stepX;
                i >= 0 && j >= 0 && i < y + length && j < x + length;
                i += stepY, j += stepX) {
            if (field[i][j] == cellSign) {
                --toGo;
            } else if (field[i][j] != CELL_EMPTY) {
                break;
            }
        }
        return toGo;
    }

    private static int[] searchForBestMove(char cellSign) {
        int bestMovesToWin;
        int movesToWin;
        bestMovesToWin = WIN_SEQUENCE_COUNT;
        final int[] bestMoveCoords = generateInvalidCell();
        for (int y = 0; y < FIELD_SIZE_Y; y++) {
            for (int x = 0; x < FIELD_SIZE_X; x++) {
                if (isInvalidCell(x, y)) {
                    continue;
                }
                field[y][x] = cellSign;
                movesToWin = calculateMovesToWin(cellSign, x, y);
                if (movesToWin < bestMovesToWin) {
                    bestMovesToWin = movesToWin;
                    bestMoveCoords[0] = x;
                    bestMoveCoords[1] = y;
                }
                field[y][x] = CELL_EMPTY;
            }
        }
        return bestMoveCoords;
    }

    private static int calculateMovesToWin(char cellSign, int x, int y) {
        if (x > 0) {
            if (field[y][x - 1] == cellSign) {
                return calculateMovesToWin(cellSign, x - 1, y);
            }
            if (y > 0 && field[y - 1][x - 1] == cellSign) {
                return calculateMovesToWin(cellSign, x - 1, y - 1);
            }
        }
        if (y > 0) {
            if (field[y - 1][x] == cellSign) {
                return calculateMovesToWin(cellSign, x, y - 1);
            }
            if (x < FIELD_SIZE_X - 1 && field[y - 1][x + 1] == cellSign) {
                return calculateMovesToWin(cellSign, x + 1, y - 1);
            }
        }
        return Math.min(
                Math.min(
                        searchForSequenceFromPoint(x, y, 1, 0, WIN_SEQUENCE_COUNT, cellSign),
                        searchForSequenceFromPoint(x, y, 0, 1, WIN_SEQUENCE_COUNT, cellSign)
                ),
                Math.min(
                        searchForSequenceFromPoint(x, y, 1, 1, WIN_SEQUENCE_COUNT, cellSign),
                        searchForSequenceFromPoint(x, y, -1, 1, WIN_SEQUENCE_COUNT, cellSign)
                )
        );
    }

    private static int[] searchForWinMove(char cellSign) {
        for (int y = 0; y < FIELD_SIZE_Y; y++) {
            for (int x = 0; x < FIELD_SIZE_X; x++) {
                if (isInvalidCell(x, y)) {
                    continue;
                }
                field[y][x] = cellSign;
                boolean success = checkWinConditionsForPlayer(cellSign);
                field[y][x] = CELL_EMPTY;
                if (success) {
                    return new int[] {x, y};
                }
            }
        }
        return generateInvalidCell();
    }

    private static int[] generateInvalidCell() {
        return new int[] {-1, -1};
    }

    private static boolean isInvalidCell(int[] coordinates) {
        return isInvalidCell(coordinates[0], coordinates[1]);
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
        return isInvalidCell(x, y) ? aiRandomInput() : new int[] {x, y};
    }

    private static int[] aiInput() {
        final int[] winMoveAICell = searchForWinMove(AI_SIGN);
        if (!isInvalidCell(winMoveAICell)) {
            return winMoveAICell;
        }
        final int[] breakHumanWinCell = searchForWinMove(HUMAN_SIGN);
        if (!isInvalidCell(breakHumanWinCell)) {
            return breakHumanWinCell;
        }
        final int[] bestMoveCell = searchForBestMove(AI_SIGN);
        if (!isInvalidCell(bestMoveCell)) {
            return bestMoveCell;
        }
        return aiRandomInput();
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