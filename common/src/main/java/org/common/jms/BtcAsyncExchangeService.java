package org.common.jms;

public interface BtcAsyncExchangeService<T> {

  void asyncExchange(T entity);
}
