package com.jbtits.geekbrains.lv1.lesson6.utils;

import java.io.*;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Утилитный класс для работы с файлами
 *
 * @author Nikolay Zaytsev
 */
public class FileUtils {

    public static final char NEW_LINE = '\n';

    private FileUtils() {}

    /**
     * Объединяет несколько файлов в один входной поток.
     *
     * Ответственность за закрытие объединенного вводного потока лежит на вызывающем коде. Закрытие производится
     * методом {@link MultiInputStream#close()}
     *
     * @param separatorByte Разделительный байт, вставляемый между контентом склеиваемых файлов
     * @param files Множество имен файлов для склейки
     * @return Объединеный вводной поток
     */
    private static InputStream mergeFiles(int separatorByte, File ... files) {
        return new MultiInputStream(separatorByte, files);
    }

    public static void mergeFiles(File target, int separatorByte, File ... sources) throws IOException {
        try (final FileOutputStream outputStream = new FileOutputStream(target);
                final InputStream inputStream = mergeFiles(separatorByte, sources)) {
            int read;
            while ((read = inputStream.read()) >= 0) {
                outputStream.write(read);
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public static boolean directorySearch(File dir, String needle) throws IOException {
        if (!dir.exists() || !dir.isDirectory()) {
            throw new FileNotFoundException("Can't open directory "  + dir.getAbsolutePath());
        }
        if (dir.listFiles() == null) {
            return false;
        }
        final File[] filesInDirectory = Stream.of(dir.listFiles())
                .filter(File::isFile)
                .toArray(File[]::new);
        return search(mergeFiles(NEW_LINE, filesInDirectory), needle);
    }

    public static boolean fileSearch(File file, String needle) throws IOException {
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
    private static boolean search(InputStream inputStream, String needle) throws IOException {
        if (needle == null || needle.isEmpty()) {
            throw new IllegalArgumentException("Needle must be non empty string");
        }
        final byte[] bytes = needle.getBytes();
        int index = 0;
        try {
            int read;
            while ((read = inputStream.read()) >= 0) {
                if (read == bytes[index]) {
                    index++;
                } else if (read == bytes[0]) {
                    index = 1;
                    continue;
                } else {
                    index = 0;
                }
                if (index == bytes.length) {
                    return true;
                }
            }
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            inputStream.close();
        }
        return false;
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
        public int read(byte[] b, int off, int len) throws IOException {
            throw new UnsupportedOperationException("Not realized. Please, use read()");
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
