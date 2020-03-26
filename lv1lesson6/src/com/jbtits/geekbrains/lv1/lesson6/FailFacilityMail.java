package com.jbtits.geekbrains.lv1.lesson6;

import com.jbtits.geekbrains.lv1.lesson6.utils.FileUtils;

import java.io.*;

/**
 * @author Nikolay Zaytsev
 */
public class FailFacilityMail {

    private static final File SOURCE_1 = new File("resources/lorem.txt");
    private static final File SOURCE_2 = new File("resources/sed.txt");
    private static final File TARGET = new File("out/merge.txt");
    private static final File DIR = new File("resources");

    public static void main(String[] args) {
        mergeFiles(TARGET, SOURCE_1, SOURCE_2);
        searchInFile("Lorem", SOURCE_1);
        searchInFile("lorem", SOURCE_2);
        searchInDirectory("sed", DIR);
        searchInDirectory("Sed", DIR);
    }

    private static void searchInDirectory(String needle, File dir) {
        try {
            System.out.printf("Searching for '%s' in directory %s files. Result is %b", needle, dir.getPath(),
                    FileUtils.directorySearch(dir, needle));
            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException("Unable to read searching directory files content", e);
        }
    }

    private static void searchInFile(String needle, File file) {
        try {
            System.out.printf("Searching for '%s' in file %s. Result is %b", needle, file.getPath(),
                    FileUtils.fileSearch(file, needle));
            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException("Unable to read searching file content", e);
        }
    }

    private static void mergeFiles(File target, File ... sources) {
        try {
            FileUtils.mergeFiles(target, FileUtils.NEW_LINE, sources);
        } catch (IOException e) {
            throw new RuntimeException("Can't merge files", e);
        }
    }
}
