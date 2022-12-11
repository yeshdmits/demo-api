package org.restservice.controller;

import java.util.Optional;
import org.restservice.model.Account;
import org.restservice.service.DbEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {

  private final DbEntityService<Account> accountService;

  public AccountController(DbEntityService<Account> accountService) {
    this.accountService = accountService;
  }

  @PostMapping
  public ResponseEntity<Account> createAccount(@RequestParam Optional<String> name,
      @RequestParam(defaultValue = "0") Double usdBalance) {
    return name
        .map(accName -> accountService.create(
            Account.builder()
                .name(accName)
                .usdBalance(usdBalance)
                .build()))
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.badRequest().build());
  }

  @GetMapping("/{accountId}")
  public ResponseEntity<Account> fetchAccountDetails(@PathVariable String accountId) {
    return accountService.read(accountId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.noContent().build());
  }
}
