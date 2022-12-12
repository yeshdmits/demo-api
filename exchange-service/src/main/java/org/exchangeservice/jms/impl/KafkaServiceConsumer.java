package org.exchangeservice.jms.impl;

import lombok.extern.slf4j.Slf4j;
import org.common.dto.OrderDto;
import org.common.exception.ExchangeException;
import org.common.jms.AsyncCallService;
import org.common.jms.JmsServiceConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Consumer kafka implementation
 *
 * @author yeshenkodmit
 */
@Slf4j
@Service
public class KafkaServiceConsumer implements JmsServiceConsumer<OrderDto> {

  /**
   * Service for parallel thread creating
   */
  private final AsyncCallService service;

  public KafkaServiceConsumer(AsyncCallService service) {
    this.service = service;
  }

  /**
   * Consumer kafka listener Setting order for parallel thread Creating thread
   *
   * @param order Fetched entity from kafka
   */
  @Override
  @KafkaListener(topics = "create_order", groupId = "order_group_id")
  public void consume(OrderDto order) {
    if (order == null || order.getId() == null || order.getId().isBlank()) {
      log.debug("Error while consuming message. Message cannot be null.");
      throw new ExchangeException("Error while consuming message. Message cannot be null.");
    }
    log.debug("Consumed message: {}", order);
    service.setOrder(order);
    service.asyncCall();
  }
}
