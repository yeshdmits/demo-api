package org.restservice.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.common.dto.OrderDto;
import org.common.exception.ExchangeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.restservice.model.Account;
import org.restservice.model.Order;
import org.restservice.service.DbEntityService;

/**
 * @author yeshenkodmit
 */
@ExtendWith(MockitoExtension.class)
class ExchangeBtcServiceTest {

  private ExchangeBtcService service;

  @Mock
  private DbEntityService<Account> accountService;

  @Mock
  private DbEntityService<Order> orderService;

  @BeforeEach
  public void setUp() {
    this.service = new ExchangeBtcService(accountService, orderService);
  }

  @Test
  public void shouldSuccessfullyExchange() {
    //given
    Account account = Account.builder().id("1")
        .usdBalance(500.)
        .btcBalance(10.).build();

    Order order = Order.builder().id("1")
        .exchangePrice(100.).priceLimit(101.)
        .account(account).amount(400.).build();

    OrderDto orderDto = OrderDto.builder().id("1").priceLimit(101.).exchangePrice(100.).build();

    //when
    when(orderService.read("1")).thenReturn(Optional.of(order));
    when(accountService.read("1")).thenReturn(Optional.of(account));

    service.asyncExchange(orderDto);

    //then
    verify(accountService).update(any(Account.class));
    verify(orderService).update(any(Order.class));
  }

  @Test
  public void shouldThrowExchangeExceptionCannotFindOrder() {
    //given
    OrderDto orderDto = OrderDto.builder().id("1").priceLimit(101.).exchangePrice(100.).build();

    //when
    when(orderService.read("1")).thenReturn(Optional.empty());

    //then
    Assertions.assertThrows(ExchangeException.class, () -> service.asyncExchange(orderDto));
  }

  @Test
  public void shouldThrowExchangeExceptionCannotFindAccount() {
    Order order = Order.builder().id("1")
        .exchangePrice(100.).priceLimit(101.)
        .account(Account.builder().id("1").build())
        .amount(400.).build();

    OrderDto orderDto = OrderDto.builder().id("1").priceLimit(101.).exchangePrice(100.).build();

    //when
    when(orderService.read("1")).thenReturn(Optional.of(order));
    when(accountService.read("1")).thenReturn(Optional.empty());

    //then
    Assertions.assertThrows(ExchangeException.class, () -> service.asyncExchange(orderDto));
  }

  @Test
  public void shouldThrowExceptionNotEnoughUsdBalance() {
    //given
    Account account = Account.builder().id("1")
        .usdBalance(500.)
        .btcBalance(10.).build();

    Order order = Order.builder().id("1")
        .exchangePrice(100.).priceLimit(101.)
        .account(account).amount(600.).build();

    OrderDto orderDto = OrderDto.builder().id("1").priceLimit(101.).exchangePrice(100.).build();

    //when
    when(orderService.read("1")).thenReturn(Optional.of(order));
    when(accountService.read("1")).thenReturn(Optional.of(account));

    //then
    Assertions.assertThrows(ExchangeException.class, () -> service.asyncExchange(orderDto));
  }

  @Test
  public void shouldThrowExceptionIncorrectExchangePrice() {
    //given
    Account account = Account.builder().id("1")
        .usdBalance(500.)
        .btcBalance(10.).build();

    Order order = Order.builder().id("1")
        .exchangePrice(110.).priceLimit(101.)
        .account(account).amount(400.).build();

    OrderDto orderDto = OrderDto.builder().id("1").priceLimit(101.).exchangePrice(0.).build();

    //when
    when(orderService.read("1")).thenReturn(Optional.of(order));
    when(accountService.read("1")).thenReturn(Optional.of(account));

    //then
    Assertions.assertThrows(ExchangeException.class, () -> service.asyncExchange(orderDto));
  }

}