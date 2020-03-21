package com.jbtits.geekbrains.lv1.lesson6.utils;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

/**
 * Утилитный класс для работы с файлами
 *
 * @author Nikolay Zaytsev
 */
public class FileUtils {

    public static final char NEW_LINE = '\n';

    private static final String EMPTY_STRING = "";

    private FileUtils() {}

    /**
     * Объединяет несколько файлов в один входной поток.
     *
     * Ответственность за закрытие объединенного вводного потока лежит на вызывающем коде. Закрытие производится методом {@link MultiInputStream#close()}
     *
     * @param separatorByte Разделительный байт, вставляемый между контентом склеиваемых файлов
     * @param files Множество имен файлов для склейки
     * @return Объединеный вводной поток
     */
    private static InputStream mergeFiles(int separatorByte, File ... files) {
        return new MultiInputStream(separatorByte, files);
    }

    public static void mergeFiles(File target, int separatorByte, File ... sources) throws FileNotFoundException {
        final FileOutputStream outputStream = new FileOutputStream(target);
        final PrintStream printStream = new PrintStream(outputStream);
        final Scanner scanner = new Scanner(mergeFiles(separatorByte, sources));
        while (scanner.hasNextLine()) {
            printStream.println(scanner.nextLine());
        }
        scanner.close();
        printStream.close();
    }

    public static boolean directorySearch(File dir, String needle) throws FileNotFoundException {
        if (!dir.exists() || !dir.isDirectory()) {
            throw new FileNotFoundException("Can't open directory "  + dir.getAbsolutePath());
        }
        return search(mergeFiles(NEW_LINE, dir.listFiles()), needle);
    }

    public static boolean fileSearch(File file, String needle) throws FileNotFoundException {
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("Can't open file "  + file.getAbsolutePath());
        }
        final FileInputStream inputStream = new FileInputStream(file);
        return search(inputStream, needle);
    }

    /**
     * Поиск слова в потоке. Поиск чувствителен к регистру
     *
     * @param inputStream Вводной поток, в котором будет осуществляться поиск
     * @param needle Слово
     * @return true, если слово удалось найти в файле
     */
    private static boolean search(InputStream inputStream, String needle) {
        final Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            final String word = extractWord(scanner.next());
            if (needle.equals(word)) {
                scanner.close();
                return true;
            }
        }
        scanner.close();
        return false;
    }

    private static String extractWord(String token) {
        final char[] word = new char[token.length()];
        int pos = 0;
        for (int i = 0; i < token.length(); i++) {
            final char letter = token.charAt(i);
            if (letter >= 'A' && letter <= 'Z' || letter >= 'a' && letter <= 'z') {
                word[pos++] = letter;
            } else {
                return EMPTY_STRING;
            }
        }
        return new String(word, 0, pos);
    }

    private static class MultiInputStream extends InputStream {

        private static final int NO_SEPARATOR = -1;
        private static final int BYTE_RANGE_MIN = 0;
        private static final int BYTE_RANGE_MAX = 0xFF;

        private final int separatorByte;
        private final File[] files;
        private InputStream currentInputStream;
        private int currentFile;

        private MultiInputStream(File ... files) {
            this(NO_SEPARATOR, files);
        }

        private MultiInputStream(int separatorByte, File ... files) {
            assert separatorByte >= BYTE_RANGE_MIN && separatorByte <= BYTE_RANGE_MAX;
            this.separatorByte = separatorByte;
            this.files = files;
            this.currentFile = 0;
        }

        @Override
        public int read() throws IOException {
            if (currentFile == files.length) {
                return -1;
            }
            if (Objects.isNull(currentInputStream)) {
                currentInputStream = new FileInputStream(files[currentFile]);
            }
            final int readByte = currentInputStream.read();
            if (readByte >= 0) {
                return readByte;
            } else {
                currentInputStream.close();
                currentInputStream = null;
                currentFile++;
                if (separatorByte < 0) {
                    return this.read();
                } else {
                    return separatorByte;
                }
            }
        }

        @Override
        public void close() throws IOException {
            if (Objects.nonNull(currentInputStream)) {
                currentInputStream.close();
            }
            currentFile = files.length;
        }
    }
}
