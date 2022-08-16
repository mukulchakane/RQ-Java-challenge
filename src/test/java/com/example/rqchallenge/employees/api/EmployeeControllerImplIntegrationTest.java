package com.example.rqchallenge.employees.api;

import com.example.rqchallenge.employees.models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* This class contains integration test cases to verify REST API endpoints (request and response)
* in EmployeeControllerImpl class.
* For mocking API request MockWebServer is used & for calling REST API endpoints WebTestClient is used.
*/

class EmployeeControllerImplIntegrationTest {

    private String BASE_URL;

    public static MockWebServer mockBackEnd;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();

    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize() {
        BASE_URL = String.format("http://localhost:%s",
                mockBackEnd.getPort());
    }

    @Test
    void testWhenGetAllEmployeesApiCalledThenShouldSuccess() throws JsonProcessingException {
        Employee mockEmployee1 = new Employee("1", "Garrett Winters", 170750, "43");
        Employee mockEmployee2 = new Employee("2", "John Mayer", 270750, "33");
        List<Employee> employees = new ArrayList<>();
        employees.add(mockEmployee1);
        employees.add(mockEmployee2);
        GetAllEmployeeApiResponse mockGetAllEmployeeApiResponse = new GetAllEmployeeApiResponse();
        mockGetAllEmployeeApiResponse.setStatus("success");
        mockGetAllEmployeeApiResponse.setData(employees);
        mockGetAllEmployeeApiResponse.setMessage("Successfully! All records has been fetched.");
        ObjectMapper objectMapper = new ObjectMapper();
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(mockGetAllEmployeeApiResponse))
                .addHeader("Content-Type", "application/json"));

        WebTestClient
                .bindToServer().baseUrl(BASE_URL)
                .build()
                .get()
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("status").isEqualTo("success")
                .jsonPath("data").isArray()
                .jsonPath("message").isEqualTo("Successfully! All records has been fetched.");
    }

    @Test
    void testWhenGetEmployeesByNameSearchApiCalledThenShouldSuccess() throws JsonProcessingException {
        Employee mockEmployee1 = new Employee("1", "Garrett Winters", 170750, "43");
        Employee mockEmployee2 = new Employee("2", "Tiger Nixon", 270750, "33");
        List<Employee> employees = new ArrayList<>();
        employees.add(mockEmployee1);
        employees.add(mockEmployee2);
        GetAllEmployeeApiResponse mockGetAllEmployeeApiResponse = new GetAllEmployeeApiResponse();
        mockGetAllEmployeeApiResponse.setStatus("success");
        mockGetAllEmployeeApiResponse.setData(employees);
        mockGetAllEmployeeApiResponse.setMessage("Successfully! All records has been fetched.");
        ObjectMapper objectMapper = new ObjectMapper();
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(mockGetAllEmployeeApiResponse))
                .addHeader("Content-Type", "application/json"));

        WebTestClient.bindToServer().baseUrl(BASE_URL)
                .build()
                .get()
                .uri("/search/Tiger%20Nixon")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("status").isEqualTo("success")
                .jsonPath("data").isArray()
                .jsonPath("message").isEqualTo("Successfully! All records has been fetched.");
    }

    @Test
    void testWhenGetEmployeeByIdApiCalledThenShouldSuccess() throws JsonProcessingException {
        Employee mockEmployee = new Employee("2", "Garrett Winters",170750, "63");
        GetEmployeeApiResponse mockGetEmployeeApiResponse = new GetEmployeeApiResponse();
        mockGetEmployeeApiResponse.setStatus("success");
        mockGetEmployeeApiResponse.setData(mockEmployee);
        mockGetEmployeeApiResponse.setMessage("Successfully! Record has been fetched.");
        ObjectMapper objectMapper = new ObjectMapper();
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(mockGetEmployeeApiResponse))
                .addHeader("Content-Type", "application/json"));

        WebTestClient.bindToServer().baseUrl(BASE_URL)
                .build()
                .get()
                .uri("/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("status").isEqualTo("success")
                .jsonPath("data.id").isEqualTo(2)
                .jsonPath("data.employee_name").isEqualTo("Garrett Winters")
                .jsonPath("data.employee_salary").isEqualTo(170750)
                .jsonPath("data.employee_age").isEqualTo(63)
                .jsonPath("message").isEqualTo("Successfully! Record has been fetched.");
    }

    @Test
    void testWhenGetHighestSalaryOfEmployeesApiCalledThenShouldSuccess() {
        mockBackEnd.enqueue(new MockResponse()
                .setBody(String.valueOf(725000))
                .addHeader("Content-Type", "application/json"));

        WebTestClient
                .bindToServer().baseUrl(BASE_URL)
                .build()
                .get()
                .uri("/highestSalary")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Integer.class).isEqualTo(725000);
    }

    @Test
    void testWhenGetTopTenHighestEarningEmployeeNamesApiCalledThenShouldSuccess() throws JsonProcessingException {
        List<String> mockEmployeeNames = new ArrayList<>();
        mockEmployeeNames.add("Paul Byrd");
        mockEmployeeNames.add("Yuri Berry");
        mockEmployeeNames.add("Charde Marshall");
        mockEmployeeNames.add("Cedric Kelly");
        mockEmployeeNames.add("Tatyana Fitzpatrick");
        mockEmployeeNames.add("Brielle Williamson");
        mockEmployeeNames.add("Jenette Caldwell");
        mockEmployeeNames.add("Quinn Flynn");
        mockEmployeeNames.add("Rhona Davidson");
        mockEmployeeNames.add("Tiger Nixon");
        ObjectMapper objectMapper = new ObjectMapper();
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(mockEmployeeNames))
                .addHeader("Content-Type", "application/json"));

        WebTestClient
                .bindToServer().baseUrl(BASE_URL)
                .build()
                .get()
                .uri("/topTenHighestEarningEmployeeNames")
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class).isEqualTo(mockEmployeeNames);
    }

    @Test
    void testWhenCreateEmployeeApiCalledThenShouldCreateNewEmployeeRecord() throws JsonProcessingException {
        CreateEmployee mockEmployee = new CreateEmployee();
        mockEmployee.setId("2");
        mockEmployee.setName("John Mayer");
        mockEmployee.setAge("25");
        mockEmployee.setSalary("100000");
        CreateEmployeeApiResponse mockCreateEmployeeApiResponse = new CreateEmployeeApiResponse();
        mockCreateEmployeeApiResponse.setStatus("success");
        mockCreateEmployeeApiResponse.setData(mockEmployee);
        mockCreateEmployeeApiResponse.setMessage("Successfully! Record has been added.");
        ObjectMapper objectMapper = new ObjectMapper();
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(mockCreateEmployeeApiResponse))
                .addHeader("Content-Type", "application/json")
                .setStatus(HttpStatus.CREATED.toString())
                .setResponseCode(201));

        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "John Mayer");
        payload.put("salary", "100000");
        payload.put("age", "25");
        WebTestClient
                .bindToServer().baseUrl(BASE_URL)
                .build()
                .post()
                .bodyValue(payload)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("status").isEqualTo("success")
                .jsonPath("data.name").isEqualTo("John Mayer")
                .jsonPath("data.salary").isEqualTo("100000")
                .jsonPath("data.age").isEqualTo("25")
                .jsonPath("message").isEqualTo("Successfully! Record has been added.");
    }

    @Test
    void testWhenDeleteEmployeeByIdApiIsCalledThenShouldDeleteEmployeeRecord() throws JsonProcessingException {
        DeleteEmployeeApiResponse mockDeleteEmployeeApiResponse = new DeleteEmployeeApiResponse();
        mockDeleteEmployeeApiResponse.setData("2");
        mockDeleteEmployeeApiResponse.setStatus("success");
        mockDeleteEmployeeApiResponse.setMessage("Successfully! Record has been deleted");
        ObjectMapper objectMapper = new ObjectMapper();
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(mockDeleteEmployeeApiResponse))
                .addHeader("Content-Type", "application/json"));

        WebTestClient.bindToServer().baseUrl(BASE_URL)
                .build()
                .delete()
                .uri("/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("status").isEqualTo("success")
                .jsonPath("data").isEqualTo("2")
                .jsonPath("message").isEqualTo("Successfully! Record has been deleted");
    }
}
