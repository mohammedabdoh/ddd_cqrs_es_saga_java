package com.food.ordering.system.order.service.domain.dto.create;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
@AllArgsConstructor
public class OrderItem {
    @NonNull
    private final UUID productId;

    @NonNull
    private final Integer quantity;
    
    @NonNull
    private final BigDecimal price;
    
    @NonNull
    private final BigDecimal subTotal;
}
