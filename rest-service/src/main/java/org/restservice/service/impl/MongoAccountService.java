package org.restservice.service.impl;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.restservice.model.Account;
import org.restservice.repository.MongoAccountRepository;
import org.restservice.service.DbEntityService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MongoAccountService implements DbEntityService<Account> {

  private final MongoAccountRepository accountRepository;

  public MongoAccountService(MongoAccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Account create(Account account) {
    log.debug("Creating account: {}", account);

    Account insert = accountRepository.insert(account);
    log.info("Successfully created account details: id: {}", insert.getId());
    return insert;
  }

  @Override
  public Optional<Account> read(String accountId) {
    log.debug("Fetching account details: id: {}", accountId);

    Optional<Account> result = accountRepository.findById(accountId);
    log.info("Successfully fetched account details: {}", result.orElse(null));
    return result;
  }

  @Override
  public void update(Account account) {
    log.debug("Updating account details: id: {}", account.getId());

    if (accountRepository.findById(account.getId()).isPresent()) {
      Account save = accountRepository.save(account);
      log.info("Successfully updated account details: {}", save);
      return;
    }
    log.info("Account with id {} does not exist", account.getId());
  }
}
