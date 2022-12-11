package org.restservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.common.dto.OrderDto;
import org.common.jms.AsyncCallService;
import org.common.jms.JmsServiceConsumer;
import org.common.jms.JmsServiceProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author yeshenkodmit
 */
@Slf4j
@Service
public class KafkaService implements JmsServiceConsumer<OrderDto>, JmsServiceProducer<OrderDto> {

  private final KafkaTemplate<String, OrderDto> template;
  private final AsyncCallService service;

  public KafkaService(KafkaTemplate<String, OrderDto> template, AsyncCallService service) {
    this.template = template;
    this.service = service;
  }

  @Override
  @KafkaListener(topics = "update_order", groupId = "order_group_id")
  public void consume(OrderDto message) {
    log.debug("updating order status, start exchanging");
    service.setOrder(message);
    service.asyncCall();
  }

  @Override
  public void produce(OrderDto message) {
    template.send("create_order", message);
    log.info("Successfully sent message to jms: {}", message);
  }
}
