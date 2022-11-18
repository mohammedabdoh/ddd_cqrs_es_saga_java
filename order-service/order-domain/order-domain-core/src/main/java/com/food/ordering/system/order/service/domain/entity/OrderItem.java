package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.BaseEntity;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.OrderId;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItem extends BaseEntity<OrderItemId> {
    
    private OrderId orderId;
    private final Product product;
    private final int quantity;
    private final Money price;
    private final Money subTotal;
    
    public OrderItem(OrderId orderId, Product product, int quantity, Money price, Money subTotal) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.subTotal = subTotal;
    }

    void initializeOrderItem(OrderId orderId, OrderItemId orderItemId) {
        this.orderId = orderId;
        super.setId(orderItemId);
    }

    public boolean isPriceValid() {
        return price.isGreaterThanZero() &&
            price.equals(product.getPrice()) &&
            price.multiple(quantity).equals(subTotal);
    }
}
