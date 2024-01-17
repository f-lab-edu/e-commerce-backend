package com.kichen.creation.commerce.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class OrderRequestDto {
    @NonNull
    private List<OrderLineRequestDto> orderLineRequestDtoList;
}
