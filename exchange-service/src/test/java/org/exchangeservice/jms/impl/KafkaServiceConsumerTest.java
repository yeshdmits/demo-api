package org.exchangeservice.jms.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.common.dto.OrderDto;
import org.common.exception.ExchangeException;
import org.common.jms.AsyncCallService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author yeshenkodmit
 */
@ExtendWith(MockitoExtension.class)
class KafkaServiceConsumerTest {

  @Mock
  private AsyncCallService dependency;

  private KafkaServiceConsumer service;

  @BeforeEach
  void setUp() {
    this.service = new KafkaServiceConsumer(dependency);
  }

  @Test
  public void shouldConsumeOrderDtoAndStartAsync() {
    OrderDto order = OrderDto.builder()
        .id("1")
        .priceLimit(1.)
        .build();
    service.consume(order);

    verify(dependency).setOrder(order);
    verify(dependency).asyncCall();
  }

  @Test
  public void shouldThrowExchangeExceptionOrderIsNull() {
    assertThrows(ExchangeException.class, () -> service.consume(null));
  }

  @Test
  public void shouldThrowExchangeExceptionOrderIdIsEmpty() {
    assertThrows(ExchangeException.class, () -> service.consume(OrderDto.builder().id("").build()));
  }

}