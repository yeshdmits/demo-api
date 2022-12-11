package org.restservice.model;

public enum OrderStatus {
  INPROGRESS("in progress"),
  PROCESSED("processed"),
  ERROR("error");

  private final String status;

  OrderStatus(String status) {
    this.status = status;
  }


  @Override
  public String toString() {
    return status;
  }
}
