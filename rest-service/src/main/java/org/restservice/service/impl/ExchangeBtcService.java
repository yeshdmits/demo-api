package org.restservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.common.dto.OrderDto;
import org.common.exception.ExchangeException;
import org.common.jms.BtcAsyncExchangeService;
import org.restservice.model.Account;
import org.restservice.model.Order;
import org.restservice.model.OrderStatus;
import org.restservice.service.DbEntityService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yeshenkodmit
 */
@Slf4j
@Service
public class ExchangeBtcService implements BtcAsyncExchangeService<OrderDto> {

  private final DbEntityService<Account> accountService;
  private final DbEntityService<Order> orderService;

  public ExchangeBtcService(DbEntityService<Account> accountService,
      DbEntityService<Order> orderService) {
    this.accountService = accountService;
    this.orderService = orderService;
  }

  @Override
  public void asyncExchange(OrderDto orderDto) {
    Order order = orderService.read(orderDto.getId())
        .orElseThrow(() -> new ExchangeException("Failed exchanging. Could not find order"));
    Account account = accountService.read(order.getAccount().getId())
        .orElseThrow(() -> new ExchangeException("Failed exchanging. Could not find account"));
    try {
      order.setExchangePrice(orderDto.getExchangePrice());
      proceedExchanging(account, order);
      order.setStatus(OrderStatus.PROCESSED.toString());
    } catch (RuntimeException exception) {
      log.error(exception.getMessage());
      order.setStatus(OrderStatus.ERROR.toString());
      throw exception;
    } finally {
      saveAccountAndOrder(account, order);
    }
  }


  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @Async
  public void saveAccountAndOrder(Account account, Order order) {
    accountService.update(account);
    orderService.update(order);
  }

  private void proceedExchanging(Account account, Order order) {
    double newUsdBalance = account.getUsdBalance() - order.getAmount();
    if (newUsdBalance < 0) {
      throw new ExchangeException("Failed exchanging. Not enough usd balance");
    }
    account.setUsdBalance(newUsdBalance);
    log.debug("Set update USD balance: {}", account.getUsdBalance());
    if (order.getExchangePrice() <= 0) {
      throw new ExchangeException("Failed exchanging. Incorrect exchange price");
    }
    double newBtcBalance = order.getAmount() / order.getExchangePrice();

    account.setBtcBalance(newBtcBalance);
    log.debug("Set update BTC balance: {}", account.getBtcBalance());
  }
}
