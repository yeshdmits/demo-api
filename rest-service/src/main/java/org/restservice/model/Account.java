package org.restservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author yeshenkodmit
 */
@Document
@Getter
@Setter
@Builder
@ToString
public class Account {

  @Id
  private String id;

  private String name;

  private Double usdBalance;

  @Builder.Default
  private Double btcBalance = .0;

}
