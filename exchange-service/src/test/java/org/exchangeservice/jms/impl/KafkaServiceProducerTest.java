package org.exchangeservice.jms.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.common.dto.OrderDto;
import org.common.exception.ExchangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author yeshenkodmit
 */
@ExtendWith(MockitoExtension.class)
class KafkaServiceProducerTest {

  @Mock
  private KafkaTemplate<String, OrderDto> dependency;

  private KafkaServiceProducer service;

  @BeforeEach
  void setUp() {
    this.service = new KafkaServiceProducer(dependency);
  }

  @Test
  public void shouldProduceOrderToKafka() {
    OrderDto order = OrderDto.builder()
        .id("1")
        .priceLimit(1.)
        .build();
    service.produce(order);

    verify(dependency).send("update_order", order);
  }

  @Test
  public void shouldThrowExchangeExceptionOrderIsNull() {
    assertThrows(ExchangeException.class, () -> service.produce(null));
  }

  @Test
  public void shouldThrowExchangeExceptionOrderIdIsEmpty() {
    assertThrows(ExchangeException.class, () -> service.produce(OrderDto.builder().id("").build()));
  }

}