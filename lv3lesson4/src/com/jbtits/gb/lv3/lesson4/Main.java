package com.jbtits.gb.lv3.lesson4;

public class Main {

    private static final char[] LETTERS = {'A', 'B', 'C'};
    private static int currentLetterPos = 0;

    public static void main(String[] args) {
        for (final char letter : LETTERS) {
            new Thread(new LetterPrinter(letter)).start();
        }
    }

    public static boolean isCurrentLetter(char letter) {
        return letter == LETTERS[Main.currentLetterPos];
    }

    public static void nextLetter() {
        Main.currentLetterPos = (Main.currentLetterPos + 1) % LETTERS.length;
    }

    private static class LetterPrinter implements Runnable {

        private final char letter;

        private LetterPrinter(char letter) {
            this.letter = letter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    synchronized (LetterPrinter.class) {
                        while (!Main.isCurrentLetter(this.letter)) {
                            LetterPrinter.class.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                System.out.println(letter);
                Main.nextLetter();
                synchronized (LetterPrinter.class) {
                    LetterPrinter.class.notifyAll();
                }
            }
        }
    }
}
