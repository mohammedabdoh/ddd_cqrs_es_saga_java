package com.food.ordering.system.order.service.domain.event;

import com.food.ordering.system.domain.event.DomainEvent;
import com.food.ordering.system.order.service.domain.entity.Order;

public class OrderPaidEvent extends DomainEvent<Order> {
    public OrderPaidEvent(Order entity) {
        super(entity);
    }
}
