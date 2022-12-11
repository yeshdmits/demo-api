package org.restservice.service.impl;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.restservice.model.Order;
import org.restservice.repository.MongoOrderRepository;
import org.restservice.service.DbEntityService;
import org.springframework.stereotype.Service;

/**
 * @author yeshenkodmit
 */
@Slf4j
@Service
public class MongoOrderService implements DbEntityService<Order> {

  private final MongoOrderRepository repository;

  public MongoOrderService(MongoOrderRepository repository) {
    this.repository = repository;
  }

  @Override
  public Order create(Order entity) {
    log.debug("Creating new order entity: {}", entity);

    Order insert = repository.insert(entity);

    log.info("Successfully created limit order: {}", insert);
    return insert;
  }

  @Override
  public Optional<Order> read(String id) {
    log.debug("Fetching order entity: id {}", id);

    Optional<Order> result = repository.findById(id);

    log.info("Successfully fetched limit order: {}", result.isPresent());
    return result;
  }

  @Override
  public void update(Order order) {
    log.debug("Updating limit order: id {}", order.getId());

    if (repository.findById(order.getId()).isPresent()) {
      Order save = repository.save(order);
      log.info("Successfully updated limit order: {}", save);
      return;
    }

    log.info("Order with id {} does not exist", order.getId());
  }
}
