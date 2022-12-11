package org.exchangeservice.jms.impl;

import lombok.extern.slf4j.Slf4j;
import org.common.dto.OrderDto;
import org.common.jms.JmsServiceProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaServiceProducer implements JmsServiceProducer<OrderDto> {

  private final KafkaTemplate<String, OrderDto> kafkaTemplate;

  public KafkaServiceProducer(KafkaTemplate<String, OrderDto> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void produce(OrderDto message) {
    kafkaTemplate.send("update_order", message);
    log.info("Successfully sent message: {}", message);
  }
}
