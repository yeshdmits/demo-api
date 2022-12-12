package org.restservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.restservice.model.Account;
import org.restservice.service.DbEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author yeshenkodmit
 */
@WebMvcTest(AccountController.class)
class AccountControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private DbEntityService<Account> serviceMock;

  @Test
  public void shouldCreateAccount() throws Exception {
    Account account = Account.builder().name("accName").usdBalance(10.).btcBalance(0.).id("1")
        .build();

    Mockito.when(serviceMock.create(any(Account.class))).thenReturn(account);

    mockMvc.perform(post("/api/account").param("name", "qwe").param("usdBalance", "100."))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", Matchers.equalTo("1")))
        .andExpect(jsonPath("$.btcBalance", Matchers.equalTo(0.)));
  }

  @Test
  public void shouldFetchAccount() throws Exception {
    Account account = Account.builder().name("accName").usdBalance(10.).btcBalance(0.).id("id")
        .build();

    Mockito.when(serviceMock.read("id")).thenReturn(Optional.of(account));

    mockMvc.perform(get("/api/account/id"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", Matchers.equalTo("id")))
        .andExpect(jsonPath("$.btcBalance", Matchers.equalTo(0.)))
        .andExpect(jsonPath("$.name", Matchers.equalTo("accName")));
  }
}