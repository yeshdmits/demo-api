package org.common.jms;

public interface JmsServiceProducer<T> {

  void produce(T message);
}
