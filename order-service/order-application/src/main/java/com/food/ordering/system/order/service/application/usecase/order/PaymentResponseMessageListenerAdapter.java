package com.food.ordering.system.order.service.application.usecase.order;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.food.ordering.system.order.service.domain.message.PaymentResponse;
import com.food.ordering.system.order.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListenerPort;

@Validated
@Service
public class PaymentResponseMessageListenerAdapter implements PaymentResponseMessageListenerPort {

    @Override
    public void paymentCompleted(PaymentResponse paymentResponse) {
    
        
    }

    @Override
    public void paymentCancelled(PaymentResponse paymentResponse) {
    
        
    }
}
