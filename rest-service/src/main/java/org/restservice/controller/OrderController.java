package org.restservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.common.dto.OrderDto;
import org.common.jms.JmsServiceProducer;
import org.restservice.model.Account;
import org.restservice.model.Order;
import org.restservice.service.DbEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yeshenkodmit
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

  private final JmsServiceProducer<OrderDto> jmsService;
  private final DbEntityService<Order> orderService;
  private final DbEntityService<Account> accountService;

  public OrderController(JmsServiceProducer<OrderDto> jmsService,
      DbEntityService<Order> orderService,
      DbEntityService<Account> accountService) {
    this.jmsService = jmsService;
    this.orderService = orderService;
    this.accountService = accountService;
  }

  @Operation(summary = "createLimitOrder(account_id, price_limit, amount)")
  @PostMapping("/{accountId}")
  public ResponseEntity<OrderDto> createLimitOrder(@PathVariable String accountId,
      @RequestParam Double priceLimit,
      @RequestParam Double amount) {
    return accountService.read(accountId)
        .map(account ->
            Order.builder()
                .priceLimit(priceLimit)
                .amount(amount)
                .account(account)
                .build())
        .map(orderService::create)
        .map(order -> OrderDto.builder().id(order.getId()).priceLimit(priceLimit).build())
        .map(orderDto -> {
          jmsService.produce(orderDto);
          return ResponseEntity.ok(orderDto);
        })
        .orElse(ResponseEntity.badRequest().build());
  }

  @Operation(summary = "fetchOrderDetails(order_id)")
  @GetMapping("/{orderId}")
  public ResponseEntity<Order> fetchOrderDetails(@PathVariable String orderId) {
    return orderService.read(orderId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.badRequest().build());
  }

}
