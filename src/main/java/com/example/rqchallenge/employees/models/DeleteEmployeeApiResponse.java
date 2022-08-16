package com.example.rqchallenge.employees.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteEmployeeApiResponse {

  private String status;
  private String data;
  private String message;

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "DeleteEmployeeApiResponse{" +
            "status='" + status + '\'' +
            ", data='" + data + '\'' +
            ", message='" + message + '\'' +
            '}';
  }
}
