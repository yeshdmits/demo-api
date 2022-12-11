package org.exchangeservice.service;

import javax.annotation.PostConstruct;
import org.common.dto.OrderDto;
import org.common.jms.AsyncCallService;
import org.common.jms.BtcAsyncExchangeService;
import org.springframework.stereotype.Service;

/**
 * Implementation service for setting up asynchronous logic
 *
 * @author yeshenkodmit
 */
@Service
public class AsyncWebCallService implements AsyncCallService {

  private OrderDto order;
  private final BtcAsyncExchangeService<OrderDto> service;

  public AsyncWebCallService(BtcAsyncExchangeService<OrderDto> service) {
    this.service = service;
  }

  public void setOrder(OrderDto order) {
    this.order = order;
  }

  @PostConstruct
  public void asyncCall() {
    if (order != null) {
      service.asyncExchange(order);
    }
  }
}
