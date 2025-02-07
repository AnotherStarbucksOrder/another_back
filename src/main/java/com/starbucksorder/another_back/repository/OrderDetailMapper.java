package com.starbucksorder.another_back.repository;

import com.starbucksorder.another_back.domain.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    int save(List<OrderDetail> orderDetails, Long orderId);
}

