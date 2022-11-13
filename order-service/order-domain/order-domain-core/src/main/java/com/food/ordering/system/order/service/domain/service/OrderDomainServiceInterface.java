package com.food.ordering.system.order.service.domain.service;

import java.util.List;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;

public interface OrderDomainServiceInterface {

    OrderCreatedEvent validateAndInitiateOrder(Restaurant restaurant, Order order);

    OrderPaidEvent payOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void approveOrder(Order order);

    void cancelOrder(Order order, List<String> failureMessages);
}
