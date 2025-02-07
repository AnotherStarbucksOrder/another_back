package com.starbucksorder.another_back.service;

import com.starbucksorder.another_back.aspect.LogAspect;
import com.starbucksorder.another_back.dto.admin.request.order.ReqAdminOrderDto;
import com.starbucksorder.another_back.dto.admin.response.CMRespAdminDto;
import com.starbucksorder.another_back.dto.admin.response.order.RespOrderDetailDto;
import com.starbucksorder.another_back.dto.admin.response.order.RespOrderListDto;
import com.starbucksorder.another_back.dto.user.request.Order.ReqOrderDto;
import com.starbucksorder.another_back.domain.Order;
import com.starbucksorder.another_back.domain.User;
import com.starbucksorder.another_back.repository.CouponMapper;
import com.starbucksorder.another_back.repository.OrderDetailMapper;
import com.starbucksorder.another_back.repository.OrderMapper;
import com.starbucksorder.another_back.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private LogAspect logAspect;


    // 주문목록 저장
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean saveOrder(ReqOrderDto dto) {
        // 적립요청이 아닌 것 == 번호자체가 없음
        if (dto.getCustomer().getPhoneNumber().length() == 4) {
            save(dto, null);
            // 요청 데이터에 번호가 있는데 회원이 없는 경우 등록해주고 id 받아야함
        } else {
            User user = findUser(dto.getCustomer().getPhoneNumber());
            if (user == null) {
                // 번호등록
                user = user.builder().phoneNumber(dto.getCustomer().getPhoneNumber()).build();
                userMapper.saveUser(user);
            }
            save(dto, user.getUserId());
            // 적립
            System.out.println(user);
            System.out.println(dto.getCustomer().getUsedCoupon());
            if (dto.getCustomer().getUsedCoupon().size() == 0) {
                user.setStarCount(dto.getTotalQuantity());
                userMapper.updateStar(user);
            } else {
                couponMapper.updateUseDateById(dto.getCustomer().getUsedCoupon());
            }
        }
        return true;
    }

    public CMRespAdminDto findByDate(ReqAdminOrderDto dto) {
        int totalCount = orderMapper.countByDate(dto.toLocalDateTime());
        List<RespOrderListDto> respOrderListDto = orderMapper.findByDate(dto.toLocalDateTime()).stream().map(Order::toRespOrderListDto).collect(Collectors.toList());
        return new CMRespAdminDto(totalCount, respOrderListDto);
    }

    private User findUser(String phoneNumber) {
        User user = userMapper.findUserByPhoneNumber(phoneNumber);
        return user == null ? null : user;
    }


    // 저장
    private void save(ReqOrderDto dto, Long userId) {
        System.out.println(dto);
        System.out.println(userId);
        Order order = dto.toOrderEntity(userId);
        System.out.println(order);
        orderMapper.save(order);
        // 주문 상세 등록
        orderDetailMapper.save(order.getOrderDetails(), order.getOrderId());
    }


    public boolean updateStatus(Long orderId) {
        return orderMapper.updateStatus(orderId) > 0;
    }

    public RespOrderDetailDto getOrder(Long orderId) {
        return orderMapper.findOrderById(orderId).toRespOrderDetailDto();
    }
}