package org.restservice.repository;

import org.restservice.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yeshenkodmit
 */
@Repository
public interface MongoAccountRepository extends MongoRepository<Account, String> {

}
