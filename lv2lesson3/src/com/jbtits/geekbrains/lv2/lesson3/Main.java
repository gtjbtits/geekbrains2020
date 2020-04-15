package com.jbtits.geekbrains.lv2.lesson3;

import com.jbtits.geekbrains.lv2.lesson3.phonebook.PhoneBook;
import com.jbtits.geekbrains.lv2.lesson3.utils.TextUtils;

import java.util.*;

public class Main {

    private static final String IVANOV = "Ivanov";
    private static final String PETROV = "Petrov";

    public static void main(String[] args) {
        textTask();
        phoneBookTask();
    }

    private static void phoneBookTask() {
        System.out.println("== PhoneBook ==");
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.addPhone(PETROV, "+79160000000");
        phoneBook.addEmail(PETROV, "petrov@gmail.com");
        phoneBook.addEmail(IVANOV, "ivanov@mail.ru");
        phoneBook.add(IVANOV, "+791700000000", "ivanov@mail.ru");
        System.out.println(phoneBook);
        System.out.printf("'Ivanov' phones: %s%n", phoneBook.findPhones(IVANOV));
        System.out.printf("'Petrov' emails: %s%n", phoneBook.findEmails(PETROV));
    }

    private static void textTask() {
        final String[] text = {"Lorem", "ipsum", "dolor", "sit", "amet,", "consectetur", "adipiscing", "elit",
                "Pellentesque", "ultricies", "sem", "id", "lorem", "accumsan", "semper", "Donec", "ut", "massa",
                "efficitur,", "tempor", "diam", "in", "tincidunt", "est", "Donec", "vel", "erat", "sed", "massa",
                "ipsum", "scelerisque", "Nam", "pulvinar", "urna", "hendrerit", "est", "vulputate", "non", "luctus",
                "lacus", "congue", "Nunc", "facilisis", "yalla", "volutpat"};
        findUniqueWords(text);
        countWordEntries(text);
    }

    private static void countWordEntries(String[] text) {
        System.out.println("== Word entries ==");
        final Map<String, Integer> wordEntries = TextUtils.countWordEntries(text);
        for (Map.Entry<String, Integer> count: wordEntries.entrySet()) {
            System.out.printf("%s: %d", count.getKey(), count.getValue());
            System.out.println();
        }
    }

    private static void findUniqueWords(String[] text) {
        final Set<String> uniqueWords = TextUtils.findUniqueWords(text);
        System.out.println("== Unique words ==");
        System.out.println(uniqueWords);
    }


}
