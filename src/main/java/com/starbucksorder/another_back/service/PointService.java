package com.starbucksorder.another_back.service;

import com.starbucksorder.another_back.dto.user.response.point.RespUserDto;
import com.starbucksorder.another_back.domain.User;
import com.starbucksorder.another_back.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PointService {
    @Autowired
    private UserMapper userMapper;

    // HACK: 전화번호로 포인트와 사용자 id 가져오기
    public RespUserDto getUserIdByPhoneNumber(String phoneNumber) {
        User user = null;
        user = findUserByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        user = userMapper.findCouponsById(user.getUserId());
        if (user == null) {
            throw new UsernameNotFoundException("사용가능한 쿠폰이 없습니다");
        }
        return user.toRespUserDto();
    }

    private User findUserByPhoneNumber(String phoneNumber) {
        User user = userMapper.findUserByPhoneNumber(phoneNumber);
        return user;
    }
}
