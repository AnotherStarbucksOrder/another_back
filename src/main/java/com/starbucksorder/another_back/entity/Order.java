package com.starbucksorder.another_back.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    private Long orderId;
    private Long userId;
    private Long paymentId;
    private Long orderType;
    private int orderAmount; // 전체 주문 금액
    private Long orderState; // 결제 완료 or 취소
    private Date createDate;
}
