package com.starbucksorder.another_back.repository;

import com.starbucksorder.another_back.domain.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AdminMapper {
    // signin
    Optional<Admin> findByUserName(String username);
    Admin findById(long id);
}
