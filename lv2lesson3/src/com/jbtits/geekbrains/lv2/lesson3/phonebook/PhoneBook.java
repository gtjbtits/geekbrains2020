package com.jbtits.geekbrains.lv2.lesson3.phonebook;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.jbtits.geekbrains.lv2.lesson3.utils.TextUtils.CASE_INSENSITIVE_COMPARATOR;

/**
 * @author Nikolay Zaytsev
 */
public class PhoneBook {

    private final SortedMap<String, Set<PersonalData>> storage;

    public PhoneBook() {
        this.storage = new TreeMap<>(CASE_INSENSITIVE_COMPARATOR);
    }

    public void add(String lastName, String phone, String email) {
        this.add(lastName, new PersonalData(phone, email));
    }

    public void addPhone(String lastName, String phone) {
        this.add(lastName, new PersonalData(phone, null));
    }

    public void addEmail(String lastName, String email) {
        this.add(lastName, new PersonalData(null, email));
    }

    public Set<String> findPhones(String lastName) {
        return this.extractPersonalData(lastName, PersonalData::getPhone);
    }

    public Set<String> findEmails(String lastName) {
        return this.extractPersonalData(lastName, PersonalData::getEmail);
    }

    private void add(String lastName, PersonalData personalData) {
        if (!storage.containsKey(lastName)) {
            storage.put(lastName, new HashSet<>());
        }
        final Set<PersonalData> personalDataSet = storage.get(lastName);
        personalDataSet.add(personalData);
    }

    private <T> Set<T> extractPersonalData(String lastName, Function<PersonalData, T> extractor) {
        return this.storage.entrySet().stream()
                .filter(entry -> entry.getKey().equals(lastName))
                .flatMap(entry -> entry.getValue().stream())
                .map(extractor)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Set<PersonalData>> entry: storage.entrySet()) {
            builder.append(String.format("%s :%n", entry.getKey()));
            for (PersonalData personalData: entry.getValue()) {
                builder.append(String.format("\t%s%n", personalData.toString()));
            }
        }
        return builder.toString();
    }
}
