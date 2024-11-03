package com.starbucksorder.another_back.entity;

import com.starbucksorder.another_back.dto.admin.response.order.RespOrderDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetail {

    private Long orderDetailId;
    private Long orderId;
    private Long menuId;
    private int quantity;
    private String orderComment;

    public RespOrderDetailDto.RespOrderDetail toDetail() {
        return RespOrderDetailDto.RespOrderDetail.builder()
                .orderDetailId(orderDetailId)
                .menuId(menuId)
                .quantity(quantity)
                .orderComment(orderComment)
                .build();
    }
}
