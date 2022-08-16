package com.example.rqchallenge.employees.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAllEmployeeApiResponse {

  private String status;
  private List<Employee> data;
  private String message;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<Employee> getData() {
    return data;
  }

  public void setData(List<Employee> data) {
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
    return "GetAllEmployeeApiResponse{" +
            "status='" + status + '\'' +
            ", data=" + data +
            ", message='" + message + '\'' +
            '}';
  }
}
