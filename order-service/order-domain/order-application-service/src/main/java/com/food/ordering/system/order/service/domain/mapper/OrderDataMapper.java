package com.food.ordering.system.order.service.domain.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.food.ordering.system.domain.valueobject.CustomerId;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.OrderStatus;
import com.food.ordering.system.domain.valueobject.ProductId;
import com.food.ordering.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.service.domain.command.order.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.response.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;

@Component
public class OrderDataMapper {
    public Restaurant convertCreateOrderCommandToRestaurant(CreateOrderCommand command) {
        RestaurantId restaurantId = new RestaurantId(command.getRestaurantId());
        List<Product> products = command.getItems().stream().map(orderItem -> {
            ProductId productId = new ProductId(orderItem.getProductId());
            return new Product(productId);
        }).collect(Collectors.toList());

        return new Restaurant(
                restaurantId,
                products,
                true);
    }

    public Order createOrderFromCreateOrderCommand(CreateOrderCommand command) {
        return Order.builder()
                .customerId(new CustomerId(command.getCustomerId()))
                .restaurantId(new RestaurantId(command.getRestaurantId()))
                .deliveryAddress(createStreetAddressFromOrderAddress(command))
                .price(new Money(command.getPrice()))
                .items(createOrderItemsFromCommand(command))
                .build();
    }

    public StreetAddress createStreetAddressFromOrderAddress(CreateOrderCommand command) {
        return new StreetAddress(
                UUID.randomUUID(),
                command.getAddress().getStreet(),
                command.getAddress().getPostalCode(),
                command.getAddress().getCity());
    }

    public List<OrderItem> createOrderItemsFromCommand(CreateOrderCommand command) {
        return command.getItems().stream().map(
                commandOrderItem -> {
                    return OrderItem.builder()
                            .product(new Product(new ProductId(commandOrderItem.getProductId())))
                            .price(new Money(commandOrderItem.getPrice()))
                            .quantity(commandOrderItem.getQuantity())
                            .subTotal(new Money(commandOrderItem.getSubTotal()))
                            .build();
                }).collect(Collectors.toList());
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
