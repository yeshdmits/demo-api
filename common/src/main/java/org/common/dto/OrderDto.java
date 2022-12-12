package org.common.dto;

import java.io.Serializable;

public class OrderDto implements Serializable {

  private String id;
  private Double priceLimit;
  private Double exchangePrice;

  public OrderDto() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Double getPriceLimit() {
    return priceLimit;
  }

  public void setPriceLimit(Double priceLimit) {
    this.priceLimit = priceLimit;
  }

  public Double getExchangePrice() {
    return exchangePrice;
  }

  public void setExchangePrice(Double exchangePrice) {
    this.exchangePrice = exchangePrice;
  }

  private OrderDto(OrderDtoBuilder builder) {
    this.id = builder.id;
    this.priceLimit = builder.priceLimit;
    this.exchangePrice = builder.exchangePrice;
  }

  @Override
  public String toString() {
    return "OrderDto{" +
        "id='" + id + '\'' +
        ", priceLimit=" + priceLimit +
        ", exchangePrice=" + exchangePrice +
        '}';
  }

  public static OrderDtoBuilder builder() {
    return new OrderDtoBuilder();
  }

  public static class OrderDtoBuilder {

    private String id;
    private Double priceLimit;
    private Double exchangePrice;


    public OrderDtoBuilder id(String id) {
      this.id = id;
      return this;
    }

    public OrderDtoBuilder priceLimit(Double priceLimit) {
      this.priceLimit = priceLimit;
      return this;
    }

    public OrderDtoBuilder exchangePrice(Double exchangePrice) {
      this.exchangePrice = exchangePrice;
      return this;
    }

    public OrderDto build() {
      return new OrderDto(this);
    }
  }
}
