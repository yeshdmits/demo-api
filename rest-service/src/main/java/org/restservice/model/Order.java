package org.restservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author yeshenkodmit
 */
@Document
@Data
@Builder
public class Order {

  @Id
  private String id;

  private Double priceLimit;

  private Double amount;

  private Double exchangePrice;

  @Builder.Default
  private String status = OrderStatus.INPROGRESS.toString();

  @DBRef
  private Account account;
}
