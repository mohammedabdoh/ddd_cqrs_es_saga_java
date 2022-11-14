package com.food.ordering.system.order.service.domain.command.order;

import javax.validation.constraints.Max;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
@AllArgsConstructor
public class OrderAddress {

    @NonNull
    @Max(value = 50)
    private final String street;

    @NonNull
    @Max(value = 10)
    private final String postalCode;

    @NonNull
    @Max(value = 50)
    private final String city;
}