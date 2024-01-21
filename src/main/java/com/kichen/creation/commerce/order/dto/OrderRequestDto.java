package com.kichen.creation.commerce.order.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderRequestDto {
    private List<OrderLineRequestDto> orderLineRequestDtoList;

    public OrderRequestDto(
            @NonNull List<OrderLineRequestDto> orderLineRequestDtoList
    ) {
        validateOrderLineRequestDtoList(orderLineRequestDtoList);

        this.orderLineRequestDtoList = orderLineRequestDtoList;
    }

    private void validateOrderLineRequestDtoList(
            List<OrderLineRequestDto> orderLineRequestDtoList
    ) {
        if (orderLineRequestDtoList == null || orderLineRequestDtoList.isEmpty()) {
            throw new IllegalArgumentException("OrderLineRequestDtoList cannot be null or empty!");
        }
    }
}
