package com.starbucksorder.another_back.repository;

import com.starbucksorder.another_back.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User findUserByPhoneNumber(String phoneNumber);

    User findCouponsById(Long userId);

    User findUserByUserId(@Param("userId") Long userId);

    int saveUser(User user);

    int update(User user);

    void updateStar(User user);

    List<User> getUserAll(@Param("searchName") String searchName, @Param("startIndex") Long startIndex, @Param("limit") Long limit);

    int count(String searchName);

    int deleteByIds(List<Long> userIds);

}
