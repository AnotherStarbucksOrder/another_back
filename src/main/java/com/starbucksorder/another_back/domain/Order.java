package com.starbucksorder.another_back.domain;

import com.starbucksorder.another_back.dto.admin.response.order.RespOrderDetailDto;
import com.starbucksorder.another_back.dto.admin.response.order.RespOrderListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long orderId;

    private Long userId;
    private String paymentId;
    private Integer paymentType;
    private Long orderType;
    private Integer orderAmount; // 전체 주문 금액
    private Long orderState; // 결제 완료 or 취소

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private Integer totalQuantity;
//    private OrderDetail orderDetail;

    private List<OrderDetail> orderDetails;
    // FIXME: 결제유형 추가 됨 1 : 카드 2 : 복합결제(쿠폰 사용)

    public RespOrderListDto toRespOrderListDto() {
        return RespOrderListDto.builder()
                .orderId(orderId)
                .paymentId(paymentId)
                .paymentType(paymentType)
                .orderType(orderType)
                .orderAmount(orderAmount)
                .orderState(orderState)
                .createDate(createDate)
                .build();
    }


    public RespOrderDetailDto toRespOrderDetailDto() {
        return RespOrderDetailDto.builder()
                .orderId(orderId)
                .userId(userId)
                .paymentId(paymentId)
                .paymentType(paymentType)
                .orderType(orderType)
                .orderAmount(orderAmount)
                .orderState(orderState)
                .createDate(createDate)
                .updateDate(updateDate)
                .orderDetail(orderDetails.stream().map(OrderDetail::toDetail).collect(Collectors.toList()))
                .build();
    }
}
