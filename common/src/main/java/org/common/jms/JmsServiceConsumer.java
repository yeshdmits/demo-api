package org.common.jms;

public interface JmsServiceConsumer<T> {

  void consume(T message);
}
