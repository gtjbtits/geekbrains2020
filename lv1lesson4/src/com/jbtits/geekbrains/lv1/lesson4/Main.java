package com.jbtits.geekbrains.lv1.lesson4;

import com.jbtits.geekbrains.lv1.lesson4.domain.Employee;
import com.jbtits.geekbrains.lv1.lesson4.utils.Employees;

/**
 * @author Nikolay Zaytsev
 */
public class Main {

    public static void main(String[] args) {
        final Employee[] employees = prepareEmployeesData();
        taskFourGettersExample(employees);
        taskFiveReturnInformationAboutEmployeesOlderThan40(employees);
        taskSixRespectAllEldersEmployees(employees);
        taskSevenAverageCalculating(employees);
    }

    private static void taskSevenAverageCalculating(Employee[] employees) {
        System.out.printf("Average salary is %d", Employees.averageSalary(employees));
        System.out.println();
        System.out.printf("Average age is %d", Employees.averageAge(employees));
        System.out.println();
        System.out.println();
    }

    private static void taskSixRespectAllEldersEmployees(Employee[] employees) {
        Employees.respectElders(employees);
        for (Employee employee: employees) {
            System.out.println(employee);
        }
        System.out.println();
    }


    private static void taskFiveReturnInformationAboutEmployeesOlderThan40(Employee[] employees) {
        for (Employee employee : employees) {
            if (employee.getAge() >= 40) {
                System.out.println(employee);
            }
        }
        System.out.println();
    }

    private static void taskFourGettersExample(Employee[] employees) {
        System.out.printf("Employee %s. Position - %s", employees[0].getLastName(), employees[0].getPosition());
        System.out.println();
        System.out.println();
    }

    private static Employee[] prepareEmployeesData() {
        return new Employee[] {
                new Employee("Robinson", 15_000, 73, "musician"),
                new Employee("Al-Barkawi", 300_000, 22, "cybersportsman"),
                new Employee("Gosling", 2_000_000, 39, "actor"),
                new Employee("Reynolds", 2_000_000, 43, "actor"),
                new Employee("Durden", 500, 36, "self-employed")
        };
    }
}
