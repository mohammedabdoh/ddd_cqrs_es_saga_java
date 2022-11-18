package com.food.ordering.system.order.service.application.usecase.order;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.food.ordering.system.order.service.domain.message.RestaurantApprovalResponse;
import com.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListenerPort;

@Validated
@Service
public class RestaurantApprovalResponseMessageListenerAdapter implements RestaurantApprovalResponseMessageListenerPort {

    @Override
    public void orderApproved(RestaurantApprovalResponse response) {
        
    }

    @Override
    public void orderRejected(RestaurantApprovalResponse response) {
        
    }

}
