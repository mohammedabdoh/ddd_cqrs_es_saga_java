package com.food.ordering.system.order.service.domain.dto.track;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TrackOrderQuery {
    
    @NotNull
    private final UUID orderTrackingId;
}
