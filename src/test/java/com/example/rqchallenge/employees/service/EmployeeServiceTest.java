package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.models.Employee;
import com.example.rqchallenge.employees.models.GetAllEmployeeApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    private GetAllEmployeeApiResponse testGetAllEmployeeApiResponse;

    @BeforeEach
    void setUp() {
        testGetAllEmployeeApiResponse = new GetAllEmployeeApiResponse();
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("1", "test1", 129000, "22"));
        employees.add(new Employee("2", "test2", 220000, "23"));
        employees.add(new Employee("3", "test3", 300000, "24"));
        employees.add(new Employee("11", "test4", 400000, "24"));
        employees.add(new Employee("5", "test5", 600000, "24"));
        employees.add(new Employee("6", "test6", 700000, "24"));
        employees.add(new Employee("7", "test7", 800000, "24"));
        employees.add(new Employee("8", "test8", 900000, "24"));
        employees.add(new Employee("9", "test9", 350000, "24"));
        employees.add(new Employee("13", "test10", 300000, "24"));
        employees.add(new Employee("12", "test11", 300000, "24"));
        employees.add(new Employee("4", "Relia Quest", 229000, "25"));
        testGetAllEmployeeApiResponse.setData(employees);
        testGetAllEmployeeApiResponse.setStatus("success");
        testGetAllEmployeeApiResponse.setMessage("Successfully! All records has been fetched.");
    }

    @Test
    void testGetEmployeesByNameSearchShouldSuccess() {
        GetAllEmployeeApiResponse getAllEmployeeApiResponse =
                employeeService.getEmployeesByNameSearch(testGetAllEmployeeApiResponse, "test1");
        Optional<Employee> employee = getAllEmployeeApiResponse.getData().stream().findFirst();
        assert employee.isPresent();
        assert employee.get().getEmployee_name().equals("test1");
        assert employee.get().getId().equals("1");
        assert employee.get().getEmployee_age().equals("22");
        assert employee.get().getEmployee_salary().equals(129000);

        GetAllEmployeeApiResponse getAllEmployeeApiResponse2 =
                employeeService.getEmployeesByNameSearch(testGetAllEmployeeApiResponse, "relia quest");
        Optional<Employee> employee2 = getAllEmployeeApiResponse2.getData().stream().findFirst();
        assert employee2.isPresent();
        assert employee2.get().getEmployee_name().equals("Relia Quest");
        assert employee2.get().getId().equals("4");
        assert employee2.get().getEmployee_age().equals("25");
        assert employee2.get().getEmployee_salary().equals(229000);
    }

    @Test
    void testGetEmployeesByNameSearchShouldFail() {
        GetAllEmployeeApiResponse getAllEmployeeApiResponse =
                employeeService.getEmployeesByNameSearch(testGetAllEmployeeApiResponse, "test15");
        Optional<Employee> employee = getAllEmployeeApiResponse.getData().stream().findFirst();
        assert employee.isEmpty();
    }

    @Test
    void testGetHighestSalaryOfEmployeesShouldSuccess() {
        Integer highestSalaryOfEmployees = employeeService.getHighestSalaryOfEmployees(testGetAllEmployeeApiResponse);
        assert highestSalaryOfEmployees.equals(900000);

        Integer highestSalaryOfEmployees2 = employeeService.getHighestSalaryOfEmployees(testGetAllEmployeeApiResponse);
        assert !highestSalaryOfEmployees2.equals(30000);
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNamesShouldSuccess() {
        List<String> employeeNames = employeeService.getTopTenHighestEarningEmployeeNames(testGetAllEmployeeApiResponse);
        List<Employee> topTenHighestEarningEmployees = testGetAllEmployeeApiResponse.getData().stream()
                .sorted(Comparator.comparingInt(Employee::getEmployee_salary).reversed()).limit(10)
                .collect(Collectors.toList());
        List<String> expectedEmployeeNames = topTenHighestEarningEmployees.stream()
                .map(Employee::getEmployee_name)
                .collect(Collectors.toList());
        assert employeeNames.containsAll(expectedEmployeeNames);
    }
}
