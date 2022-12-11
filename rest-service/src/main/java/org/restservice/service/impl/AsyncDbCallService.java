package org.restservice.service.impl;

import javax.annotation.PostConstruct;
import org.common.dto.OrderDto;
import org.common.exception.ExchangeException;
import org.common.jms.AsyncCallService;
import org.common.jms.BtcAsyncExchangeService;
import org.springframework.stereotype.Service;

/**
 * @author yeshenkodmit
 */
@Service
public class AsyncDbCallService implements AsyncCallService {

  private OrderDto order;
  private final BtcAsyncExchangeService<OrderDto> service;

  public AsyncDbCallService(BtcAsyncExchangeService<OrderDto> service) {
    this.service = service;
  }

  public void setOrder(OrderDto order) {
    if (order == null) {
      throw new ExchangeException("Order value could not be null");
    }
    this.order = order;
  }

  @PostConstruct
  public void asyncCall() {
    if (order != null) {
      service.asyncExchange(order);
    }
  }
}
