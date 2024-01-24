package com.kichen.creation.commerce.order.domain;

import com.kichen.creation.commerce.order.dto.OrderResponseDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Getter
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> orderLineList = new ArrayList<>();

    @CreatedDate
    private LocalDateTime orderDate;

    private float totalCost;

    public Order(
            @NonNull List<OrderLine> orderLines
    ) {
        validateOrderLineList(orderLines);

        for (OrderLine orderLine: orderLines) {
            orderLineList.add(orderLine);
            totalCost += orderLine.getCost();
            orderLine.createOrder(this);
        }
    }

    public OrderResponseDto toOrderResponseDto() {
        return new OrderResponseDto(
                id,
                orderLineList.stream().map(OrderLine::toOrderLineResponseDto).toList(),
                orderDate,
                totalCost
        );
    }

    private void validateOrderLineList(
            List<OrderLine> orderLines
    ) {
        if (CollectionUtils.isEmpty(orderLines)) {
            throw new IllegalArgumentException("OrderLineResponseDtoList cannot be null or empty!");
        }
    }
}
