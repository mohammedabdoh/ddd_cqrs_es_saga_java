package com.food.ordering.system.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {
    private final BigDecimal amount;

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isGreaterThanZero() {
        return null != amount && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isLessThanZero() {
        return null != amount && amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public boolean isGreaterThan(Money money) {
        return null != amount && amount.compareTo(money.getAmount()) > 0;
    }

    public Money add(Money money) {
        return new Money(
            setScale(
                amount.add(money.getAmount())
            )
        );
    }

    public Money subtract(Money money) {
        return new Money(
            setScale(
                amount.subtract(money.getAmount())
            )
        );
    }

    public Money multiple(int multiplyBy) {
        return new Money(
            setScale(
                amount.multiply(new BigDecimal(multiplyBy))
            )
        );
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((amount == null) ? 0 : amount.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Money other = (Money) obj;
        if (amount == null) {
            if (other.amount != null)
                return false;
        } else if (!amount.equals(other.amount))
            return false;
        return true;
    }

    private BigDecimal setScale(BigDecimal input) {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }
}
