package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.models.Employee;
import com.example.rqchallenge.employees.models.GetAllEmployeeApiResponse;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
/**
 * This class contains logic to find employee details based on certain criteria.
 *
 */
@Service
public class EmployeeService {

    public GetAllEmployeeApiResponse getEmployeesByNameSearch(GetAllEmployeeApiResponse apiResponse, String searchString) {
        GetAllEmployeeApiResponse getAllEmployeeApiResponse = new GetAllEmployeeApiResponse();
        List<Employee> employees = apiResponse.getData().stream()
                .filter(employee -> employee.getEmployee_name().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
        getAllEmployeeApiResponse.setStatus(apiResponse.getStatus());
        getAllEmployeeApiResponse.setMessage(apiResponse.getMessage());
        getAllEmployeeApiResponse.setData(employees);
        return getAllEmployeeApiResponse;
    }

    public Integer getHighestSalaryOfEmployees(GetAllEmployeeApiResponse apiResponse) {
        Optional<Employee> employeeWithHighestSalary = apiResponse.getData().stream()
                .sorted(Comparator.comparingInt(Employee::getEmployee_salary).reversed())
                .collect(Collectors.toList()).stream().findFirst();
        return employeeWithHighestSalary.map(Employee::getEmployee_salary).orElse(null);
    }

    public List<String> getTopTenHighestEarningEmployeeNames(GetAllEmployeeApiResponse apiResponse) {
        List<Employee> topTenHighestEarningEmployees = apiResponse.getData().stream()
                .sorted(Comparator.comparingInt(Employee::getEmployee_salary).reversed()).limit(10)
                .collect(Collectors.toList());
        return topTenHighestEarningEmployees.stream().map(Employee::getEmployee_name).collect(Collectors.toList());
    }
}
