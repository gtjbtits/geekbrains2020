package com.jbtits.geekbrains.lv1.lesson4.utils;

import com.jbtits.geekbrains.lv1.lesson4.domain.Employee;

import java.util.function.ToIntFunction;

/**
 * @author Nikolay Zaytsev
 */
public class Employees {

    private static volatile long lastAssignedId;

    private Employees() {
        throw new UnsupportedOperationException("Only static usage");
    }

    public static synchronized long assignNewId() {
        return ++lastAssignedId;
    }

    private static int average(Employee[] employees, ToIntFunction<Employee> getter) {
        if (employees.length == 0) {
            return 0;
        }
        int summary = 0;
        for (Employee employee: employees) {
            summary += getter.applyAsInt(employee);
        }
        return summary / employees.length;
    }

    public static int averageSalary(Employee[] employees) {
        return average(employees, Employee::getSalary);
    }

    public static int averageAge(Employee[] employees) {
        return average(employees, Employee::getAge);
    }

    public static void respectElders(Employee[] employees) {
        for (Employee employee : employees) {
            if (employee.getAge() >= 45) {
                final int currentSalary = employee.getSalary();
                employee.setSalary(currentSalary + 5_000);
            }
        }
    }
}
