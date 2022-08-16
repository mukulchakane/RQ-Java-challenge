package com.example.rqchallenge.employees.api;

import com.example.rqchallenge.employees.models.*;
import com.example.rqchallenge.employees.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
* This class contains implementation for each REST endpoint in IEmployeeController
 * All the inputs to external endpoints and response to internal REST API are present in this class,
 * logic to fetch employee details based on certain criteria is available in {@link EmployeeService} class
*/
@RestController
public class EmployeeControllerImpl implements IEmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeControllerImpl.class);

    @Autowired
    private EmployeeService employeeService;

    private static final String DUMMY_API_URL = "https://dummy.restapiexample.com/api/v1/";

    /**
     * @return all employees data
     */
    @Override
    public ResponseEntity<GetAllEmployeeApiResponse> getAllEmployees() {
        WebClient webClient = WebClient.create();
        try {
            ResponseEntity<GetAllEmployeeApiResponse> response = webClient.get()
                    .uri(DUMMY_API_URL + "/employees")
                    .retrieve().toEntity(GetAllEmployeeApiResponse.class)
                    .block();
            assert response != null;
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(String.format("Exception while fetching all employees data %s", e.getMessage()));
            GetAllEmployeeApiResponse getAllEmployeeApiResponse = new GetAllEmployeeApiResponse();
            getAllEmployeeApiResponse.setStatus("Failed");
            getAllEmployeeApiResponse.setMessage("Error while fetching all employees data");
            return new ResponseEntity<>(getAllEmployeeApiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param searchString name of the employee
     * @return employee data for given employee_name
     */
    @Override
    public ResponseEntity<GetAllEmployeeApiResponse> getEmployeesByNameSearch(String searchString) {
        WebClient webClient = WebClient.create();
        try {
            ResponseEntity<GetAllEmployeeApiResponse> response = webClient.get()
                    .uri(DUMMY_API_URL + "/employees")
                    .retrieve().toEntity(GetAllEmployeeApiResponse.class)
                    .block();

            assert response != null;
            GetAllEmployeeApiResponse employees =
                    employeeService.getEmployeesByNameSearch(Objects.requireNonNull(response.getBody()), searchString);
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            log.error(String.format("Exception while fetching employees data %s", e.getMessage()));
            GetAllEmployeeApiResponse getAllEmployeeApiResponse = new GetAllEmployeeApiResponse();
            getAllEmployeeApiResponse.setStatus("Failed");
            getAllEmployeeApiResponse.setMessage("Error while fetching all employees data by name");
            return new ResponseEntity<>(getAllEmployeeApiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param id unique id of the employee
     * @return employee data for given id
     */
    @Override
    public ResponseEntity<GetEmployeeApiResponse> getEmployeeById(String id) {
        WebClient webClient = WebClient.create();
        try {
            ResponseEntity<GetEmployeeApiResponse> response = webClient.get()
                    .uri(DUMMY_API_URL + "/employee/" + id)
                    .retrieve().toEntity(GetEmployeeApiResponse.class)
                    .block();
            assert response != null;
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(String.format("Exception while fetching employees data %s", e.getMessage()));
            GetEmployeeApiResponse getEmployeeApiResponse = new GetEmployeeApiResponse();
            getEmployeeApiResponse.setStatus("Failed");
            getEmployeeApiResponse.setMessage("Error while fetching employee record");
            return new ResponseEntity<>(getEmployeeApiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return Highest salary of employee amongst all employees
     */
    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        WebClient webClient = WebClient.create();
        try {
            ResponseEntity<GetAllEmployeeApiResponse> response = webClient.get()
                    .uri(DUMMY_API_URL + "/employees")
                    .retrieve().toEntity(GetAllEmployeeApiResponse.class)
                    .block();

            assert response != null;
            Integer highestSalaryOfEmployees =
                    employeeService.getHighestSalaryOfEmployees(Objects.requireNonNull(response.getBody()));
            return new ResponseEntity<>(highestSalaryOfEmployees, HttpStatus.OK);
        } catch (Exception e) {
            log.error(String.format("Exception while fetching highest salary of employees %s", e.getMessage()));
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return Employee names having top 10 highest salaries
     */
    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        WebClient webClient = WebClient.create();
        try {
            ResponseEntity<GetAllEmployeeApiResponse> response = webClient.get()
                    .uri(DUMMY_API_URL + "/employees")
                    .retrieve().toEntity(GetAllEmployeeApiResponse.class)
                    .block();
            assert response != null;
            List<String> topTenHighestEarningEmployeeNames =
                    employeeService.getTopTenHighestEarningEmployeeNames(Objects.requireNonNull(response.getBody()));
            return new ResponseEntity<>(topTenHighestEarningEmployeeNames, HttpStatus.OK);
        } catch (Exception e) {
            log.error(String.format("Exception while fetching top 10 highest salaries of employees %s", e.getMessage()));
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param employeeInput contains age, name, salary
     * @return 200 OK if employee resource has been created successfully,
     * 500 Internal server error if there is any problem occurred while creating employee resource
     */
    @Override
    public ResponseEntity<CreateEmployeeApiResponse> createEmployee(Map<String, Object> employeeInput) {
        WebClient webClient = WebClient.create();
        try {
            ResponseEntity<CreateEmployeeApiResponse> response = webClient.post()
                    .uri(DUMMY_API_URL + "/create").bodyValue(employeeInput)
                    .retrieve().toEntity(CreateEmployeeApiResponse.class).block();
            assert response != null;
            return new ResponseEntity<>(response.getBody(), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(String.format("Exception while creating new employee record %s", e.getMessage()));
            CreateEmployeeApiResponse createEmployeeApiResponse = new CreateEmployeeApiResponse();
            createEmployeeApiResponse.setStatus("Failed");
            createEmployeeApiResponse.setMessage("Error while creating employee record");
            return new ResponseEntity<>(createEmployeeApiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param id unique id of employee
     * @return success message if record deleted else failed response
     */
    @Override
    public ResponseEntity<DeleteEmployeeApiResponse> deleteEmployeeById(String id) {
        WebClient webClient = WebClient.create();
        try {
            ResponseEntity<DeleteEmployeeApiResponse> response = webClient.delete()
                    .uri(DUMMY_API_URL + "/delete/" + id)
                    .retrieve().toEntity(DeleteEmployeeApiResponse.class)
                    .block();
            assert response != null;
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(String.format("Exception while deleting employees record %s", e.getMessage()));
            DeleteEmployeeApiResponse deleteEmployeeApiResponse = new DeleteEmployeeApiResponse();
            deleteEmployeeApiResponse.setStatus("Failed");
            deleteEmployeeApiResponse.setMessage("Error while deleting employee record");
            return new ResponseEntity<>(deleteEmployeeApiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
