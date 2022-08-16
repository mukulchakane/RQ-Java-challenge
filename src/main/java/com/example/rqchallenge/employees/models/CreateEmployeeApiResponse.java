package com.example.rqchallenge.employees.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateEmployeeApiResponse {
    private String status;
    private CreateEmployee data;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CreateEmployee getData() {
        return data;
    }

    public void setData(CreateEmployee data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CreateEmployeeApiResponse{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message=" + message +
                '}';
    }
}
