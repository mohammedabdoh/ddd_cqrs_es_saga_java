package com.food.ordering.system.domain.event;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import lombok.Getter;
@Getter
public abstract class DomainEvent<T> {
    protected final T entity;
    protected final ZonedDateTime createdAt;
    
    public DomainEvent(T entity) {
        this.entity = entity;
        this.createdAt = ZonedDateTime.now(ZoneId.of("UTC"));
    }   
}
