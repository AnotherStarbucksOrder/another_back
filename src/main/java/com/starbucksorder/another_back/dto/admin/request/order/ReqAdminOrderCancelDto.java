package com.starbucksorder.another_back.dto.admin.request.order;

import com.starbucksorder.another_back.domain.Order;
import lombok.Data;

@Data
public class ReqAdminOrderCancelDto {
    private Long orderId;
    private Long status;

    public Order toEntity() {
        return Order.builder()
                .orderId(orderId)
                .orderState(2l)
                .build();

    }
}