package com.jbtits.geekbrains.lv2.lesson3.phonebook;

import java.util.Objects;

/**
 * @author Nikolay Zaytsev
 */
class PersonalData {

    private final String phone;
    private final String email;

    PersonalData(String phone, String email) {
        if (Objects.isNull(phone) && Objects.isNull(email)) {
            throw new IncorrectPersonException();
        }
        this.phone = phone;
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof PersonalData) {
            final PersonalData personalData = (PersonalData) o;
            return Objects.equals(this.phone, personalData.phone)
                    && Objects.equals(this.email, personalData.email);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, email);
    }

    @Override
    public String toString() {
        return String.format("Phone: %s, email: %s", this.phone, this.email);
    }

    private static class IncorrectPersonException extends IllegalArgumentException {

        private IncorrectPersonException() {
            super("PersonalData must contain at least email or phone");
        }
    }
}
