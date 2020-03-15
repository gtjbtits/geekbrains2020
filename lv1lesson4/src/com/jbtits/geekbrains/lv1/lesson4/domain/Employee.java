package com.jbtits.geekbrains.lv1.lesson4.domain;

import java.util.function.Function;

/**
 * @author Nikolay Zaytsev
 */
public class Employee {

    private static volatile long lastAssignedId;

    private final long id;
    private String lastName;
    private int salary;
    private int age;
    private String position;

    public Employee(String lastName, int salary, int age, String position) {
        this.id = assignNewId();
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

    synchronized private static long assignNewId() {
        return ++lastAssignedId;
    }

    public static void respectElders(Employee[] employees) {
        for (Employee employee : employees) {
            if (employee.getAge() >= 45) {
                final int currentSalary = employee.getSalary();
                employee.setSalary(currentSalary + 5_000);
            }
        }
    }

    private static int average(Employee[] employees, Function<Employee, Integer> getter) {
        if (employees.length == 0) {
            return 0;
        }
        int summary = 0;
        for (Employee employee: employees) {
            summary += getter.apply(employee);
        }
        return summary / employees.length;
    }

    public static int averageSalary(Employee[] employees) {
        return average(employees, Employee::getSalary);
    }

    public static int averageAge(Employee[] employees) {
        return average(employees, Employee::getAge);
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
