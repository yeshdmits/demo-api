package org.common.dto;

import java.io.Serializable;

public class BtcPrice implements Serializable {

  private Double price;

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }
}
