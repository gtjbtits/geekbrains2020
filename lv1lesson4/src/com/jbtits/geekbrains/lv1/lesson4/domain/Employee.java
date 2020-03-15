package com.jbtits.geekbrains.lv1.lesson4.domain;

import com.jbtits.geekbrains.lv1.lesson4.utils.Employees;

/**
 * @author Nikolay Zaytsev
 */
public class Employee {

    private final long id;
    private String lastName;
    private int salary;
    private int age;
    private String position;

    public Employee(String lastName, int salary, int age, String position) {
        this.id = Employees.assignNewId();
        this.lastName = lastName;
        this.salary = salary;
        this.age = age;
        this.position = position;
    }

    public String getLastName() {
        return lastName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                ", age=" + age +
                ", position='" + position + '\'' +
                '}';
    }
}
