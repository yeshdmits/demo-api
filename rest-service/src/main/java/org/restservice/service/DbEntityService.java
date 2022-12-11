package org.restservice.service;

import java.util.Optional;

public interface DbEntityService<T> {

  T create(T entity);

  Optional<T> read(String id);

  void update(T entity);
}
