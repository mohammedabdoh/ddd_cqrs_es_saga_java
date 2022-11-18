package com.food.ordering.system.order.service.application.usecase.order;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.query.order.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.query.order.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TrackOrderHandler {

    private final OrderDataMapper orderDataMapper;
    private final OrderRepository orderRepository;

    public TrackOrderHandler(OrderDataMapper orderDataMapper, OrderRepository orderRepository) {
        this.orderDataMapper = orderDataMapper;
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    TrackOrderResponse trackOrder(TrackOrderQuery query) {
        TrackingId trackingId = new TrackingId(query.getOrderTrackingId());
        Optional<Order> order = orderRepository.findByTrackingId(trackingId);

        if(order.isEmpty()) {
            log.warn("Couldn't find order with tracking id: {}", trackingId.getValue());
            throw new OrderNotFoundException("Couldn't find order with tracking ");
        }

        return orderDataMapper.orderToOrderResponse(order.get());
    }
}
