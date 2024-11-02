package com.starbucksorder.another_back.service;

import com.starbucksorder.another_back.dto.admin.request.ReqAdminUserDto;
import com.starbucksorder.another_back.dto.admin.request.user.ReqAdminDeleteDto;
import com.starbucksorder.another_back.dto.admin.request.user.ReqAdminSearchDto;
import com.starbucksorder.another_back.dto.admin.response.menu.CMRespAdminDto;
import com.starbucksorder.another_back.dto.admin.response.user.RespAdminDto;
import com.starbucksorder.another_back.entity.User;
import com.starbucksorder.another_back.exception.DuplicateNameException;
import com.starbucksorder.another_back.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public boolean addUser(ReqAdminUserDto dto) {
        if (userMapper.findUserByPhoneNumber(dto.getPhoneNumber()) != null) {
            throw new DuplicateNameException("phone number already exist");
        }
        return userMapper.saveUser(dto.toEntity()) > 0;
    }

    // 전체조회
    public CMRespAdminDto getUserAll(ReqAdminSearchDto dto) {
        Long startIndex = (dto.getPage() - 1) * dto.getLimit();
        return new CMRespAdminDto(searchCount(dto.getSearchName()), userMapper.getUserAll(dto.getSearchName(), startIndex,dto.getLimit()).stream().map(User::toRespAdminDto).collect(Collectors.toList()));
    }

    // 검색
    public CMRespAdminDto getUserById(String searchName) {
        return new CMRespAdminDto(0, userMapper);
    }

    public RespAdminDto getUserById(Long userId) {
        return userMapper.findUserByUserId(userId).toRespAdminDto();
    }

    public boolean updateUser(ReqAdminUserDto dto) {
        if (userMapper.findUserByPhoneNumber(dto.getPhoneNumber()) != null) {
            throw new DuplicateNameException("phone number already exist");
        }
        return userMapper.update(dto.toEntity()) > 0;
    }

    public int searchCount(String searchName) {
        return userMapper.count(searchName);
    }

    // 회원 리스트 삭제
    public boolean deleteUserByIds(ReqAdminDeleteDto dto) {
        return userMapper.deleteByIds(dto.getUserIds()) > 0;
    }
}
