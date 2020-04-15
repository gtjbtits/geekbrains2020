package com.jbtits.geekbrains.lv2.lesson3.utils;

import java.util.*;

/**
 * @author Nikolay Zaytsev
 */
public class TextUtils {

    private TextUtils() {}

    public static final Comparator<String> CASE_INSENSITIVE_COMPARATOR = (o1, o2) -> {
        final String s1 = o1.toLowerCase();
        final String s2 = o2.toLowerCase();
        return s1.compareTo(s2);
    };

    public static Set<String> findUniqueWords(String[] text) {
        return new HashSet<>(Arrays.asList(text));
    }

    public static Map<String, Integer> countWordEntries(String[] text) {
        if (text == null) {
            throw new NullPointerException("text can't be null");
        }
        final Map<String, Integer> map = new TreeMap<>(CASE_INSENSITIVE_COMPARATOR);
        for (String word: text) {
            if (!map.containsKey(word)) {
                map.put(word, 1);
            } else {
                map.put(word, map.get(word) + 1);
            }
        }
        return map;
    }

}
