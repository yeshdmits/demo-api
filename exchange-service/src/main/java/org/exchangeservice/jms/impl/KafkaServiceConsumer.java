package org.exchangeservice.jms.impl;

import lombok.extern.slf4j.Slf4j;
import org.common.dto.OrderDto;
import org.common.jms.AsyncCallService;
import org.common.jms.JmsServiceConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaServiceConsumer implements JmsServiceConsumer<OrderDto> {

  private final AsyncCallService service;

  public KafkaServiceConsumer(AsyncCallService service) {
    this.service = service;
  }

  @Override
  @KafkaListener(topics = "create_order", groupId = "order_group_id")
  public void consume(OrderDto order) {
    log.debug("Consume message: {}", order);
    service.setOrder(order);
    service.asyncCall();
  }
}
