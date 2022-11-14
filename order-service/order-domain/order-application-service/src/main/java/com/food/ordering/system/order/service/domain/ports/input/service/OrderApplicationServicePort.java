package com.food.ordering.system.order.service.domain.ports.input.service;

import javax.validation.Valid;

import com.food.ordering.system.order.service.domain.command.order.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.query.order.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.query.order.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.response.CreateOrderResponse;

public interface OrderApplicationServicePort {
    CreateOrderResponse createOrder(@Valid CreateOrderCommand command);
    TrackOrderResponse trackOrder(@Valid TrackOrderQuery query);
}
