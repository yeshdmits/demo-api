package org.restservice.handler;

import org.common.exception.ExchangeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author yeshenkodmit
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<String> handleExchangeException(ExchangeException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
