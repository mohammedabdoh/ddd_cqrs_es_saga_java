package com.food.ordering.system.order.service.application.usecase.order;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.food.ordering.system.order.service.domain.command.order.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationServicePort;
import com.food.ordering.system.order.service.domain.query.order.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.query.order.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.response.CreateOrderResponse;

@Service
@Validated
class OrderApplicationServiceAdapter implements OrderApplicationServicePort {

    private final CreateOrderHandler createOrderHandler;

    private final TrackOrderHandler trackOrderHandler;

    public OrderApplicationServiceAdapter(CreateOrderHandler createOrderHandler, TrackOrderHandler trackOrderHandler) {
        this.createOrderHandler = createOrderHandler;
        this.trackOrderHandler = trackOrderHandler;
    }

    @Override
    public CreateOrderResponse createOrder(@Valid CreateOrderCommand command) {
        return createOrderHandler.createOrder(command);
    }

    @Override
    public TrackOrderResponse trackOrder(@Valid TrackOrderQuery query) {
        return trackOrderHandler.trackOrder(query);
    }
}
