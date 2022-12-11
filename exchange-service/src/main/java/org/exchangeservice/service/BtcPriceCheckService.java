package org.exchangeservice.service;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.common.dto.BtcPrice;
import org.common.dto.OrderDto;
import org.common.jms.BtcAsyncExchangeService;
import org.common.jms.JmsServiceProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * Implementation asynchronous logic
 *
 * @author yeshenkodmit
 */
@Slf4j
@Service
public class BtcPriceCheckService implements BtcAsyncExchangeService<OrderDto> {

  private final WebClient webClient;
  private final JmsServiceProducer<OrderDto> producer;

  @Value("${btc-price.maxRepeat}")
  private Long maxRepeat;

  public BtcPriceCheckService(WebClient webClient, JmsServiceProducer<OrderDto> producer) {
    this.webClient = webClient;
    this.producer = producer;
  }

  /**
   * Requesting each 2 seconds for maxRepeat property
   * or while current btc price less than order price limit
   * Produce exchanging
   *
   * @param order order data transfer object with limit price
   */
  @Async
  @Override
  public void asyncExchange(OrderDto order) {
    try {
      BtcPrice btcPrice = webClient.get()
          .retrieve()
          .bodyToMono(BtcPrice.class)
          .filter(price -> price.getPrice() < order.getPriceLimit())
          .repeatWhenEmpty(flux -> Flux.interval(Duration.ofSeconds(2)).take(maxRepeat))
          .block();

      order.setExchangePrice(btcPrice == null ? 0 : btcPrice.getPrice());
    } catch (RuntimeException exception) {
      log.error("No result of retrieving data from API");
    } finally {
      if (order != null) {
        producer.produce(order);
      }
    }
  }
}
