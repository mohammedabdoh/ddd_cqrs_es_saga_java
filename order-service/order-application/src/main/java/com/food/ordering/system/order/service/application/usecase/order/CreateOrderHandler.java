package com.food.ordering.system.order.service.application.usecase.order;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.food.ordering.system.order.service.domain.command.order.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;
import com.food.ordering.system.order.service.domain.response.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.service.OrderDomainServiceInterface;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CreateOrderHandler {

    private final OrderDomainServiceInterface orderDomainService;

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final RestaurantRepository restaurantRepository;

    private final OrderDataMapper orderDataMapper;

    private final OrderCreatedPaymentRequestMessagePublisher messagePublisher;

    public CreateOrderHandler(OrderDomainServiceInterface orderDomainService, OrderRepository orderRepository,
            CustomerRepository customerRepository, RestaurantRepository restaurantRepository,
            OrderDataMapper orderDataMapper, OrderCreatedPaymentRequestMessagePublisher messagePublisher) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
        this.messagePublisher = messagePublisher;
    }

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand command) {
        checkCustomer(command.getCustomerId());
        Restaurant restaurant = checkRestaurant(command);

        Order order = orderDataMapper.createOrderFromCreateOrderCommand(command);
        OrderCreatedEvent event = orderDomainService.validateAndInitiateOrder(restaurant, order);

        Order createdOrder = saveOrder(order);
        messagePublisher.publish(event);

        return orderDataMapper.orderToCreateOrderResponse(createdOrder);
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if (customer.isEmpty()) {
            log.warn("Couldn't find customer with id: {}", customerId);
            throw new OrderDomainException("Customer does not exist");
        }
    }

    private Restaurant checkRestaurant(CreateOrderCommand command) {
        Restaurant restaurant = orderDataMapper.convertCreateOrderCommandToRestaurant(command);
        Optional<Restaurant> foundRestaurant = restaurantRepository.findRestaurantInformation(restaurant);

        if (foundRestaurant.isEmpty()) {
            log.warn("Couldn't find restaurant with id: {}", command.getRestaurantId());
            throw new OrderDomainException("Restaurant does not exist");
        }

        return foundRestaurant.get();
    }

    private Order saveOrder(Order order) {
        Order orderResult = orderRepository.save(order);

        if (orderResult == null) {
            throw new OrderDomainException("Could not save order");
        }

        log.info("Order saved in the database with id: {}", orderResult.getId().getValue());

        return orderResult;
    }
}
