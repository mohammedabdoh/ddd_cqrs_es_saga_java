package com.food.ordering.system.order.service.domain.service;

import java.util.List;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderDomainService implements OrderDomainServiceInterface {

    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Restaurant restaurant, Order order) 
    {
        validateRestaurant(restaurant);
        setOrderProductInformation(order, restaurant);

        order.validateOrder();
        order.initializeOrder();

        log.info(String.format("Order %s has been created", order.getId().getValue()));

        return new OrderCreatedEvent(order);
    }

    @Override
    public OrderPaidEvent payOrder(Order order) 
    {
        order.pay();

        log.info(String.format("Order %s has been paid", order.getId().getValue()));

        return new OrderPaidEvent(order);
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) 
    {
        order.initCancel(failureMessages);
        
        log.info(String.format("Order payment is cancelling for order %s", order.getId().getValue()));

        return new OrderCancelledEvent(order);
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();

        log.info(String.format("Order %s has been approved", order.getId().getValue()));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info(String.format("Order %s has been cancelled", order.getId().getValue()));
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.isActive()) {
            throw new OrderDomainException("Restaurant is not active. Order can not be placed");
        }
    }

    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        order.getItems().forEach(
                orderItem -> {
                    restaurant.getProducts().forEach(
                            restaurantProcuct -> {
                                Product currentProduct = orderItem.getProduct();
                                if (currentProduct.equals(restaurantProcuct)) {
                                    currentProduct.updateWithConfirmedNameAndPrice(restaurantProcuct.getName(),
                                            restaurantProcuct.getPrice());
                                }
                            });
                });
    }
}
