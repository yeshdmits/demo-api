package org.restservice.repository;

import org.restservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yeshenkodmit
 */
@Repository
public interface MongoOrderRepository extends MongoRepository<Order, String> {

}
