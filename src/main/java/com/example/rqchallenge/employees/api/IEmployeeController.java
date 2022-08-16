package com.example.rqchallenge.employees.api;

import com.example.rqchallenge.employees.models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequestMapping("/employees")
public interface IEmployeeController {

    @GetMapping()
    ResponseEntity<GetAllEmployeeApiResponse> getAllEmployees() throws IOException;

    @GetMapping("/search/{searchString}")
    ResponseEntity<GetAllEmployeeApiResponse> getEmployeesByNameSearch(@PathVariable String searchString);

    @GetMapping("/{id}")
    ResponseEntity<GetEmployeeApiResponse> getEmployeeById(@PathVariable String id);

    @GetMapping("/highestSalary")
    ResponseEntity<Integer> getHighestSalaryOfEmployees();

    @GetMapping("/topTenHighestEarningEmployeeNames")
    ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames();

    @PostMapping()
    ResponseEntity<CreateEmployeeApiResponse> createEmployee(@RequestBody Map<String, Object> employeeInput);

    @DeleteMapping("/{id}")
    ResponseEntity<DeleteEmployeeApiResponse> deleteEmployeeById(@PathVariable String id);

}
