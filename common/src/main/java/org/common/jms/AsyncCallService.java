package org.common.jms;

import org.common.dto.OrderDto;

public interface AsyncCallService {

  void asyncCall();

  void setOrder(OrderDto entity);
}
