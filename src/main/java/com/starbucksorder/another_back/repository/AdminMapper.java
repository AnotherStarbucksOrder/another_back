package com.starbucksorder.another_back.repository;

import com.starbucksorder.another_back.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Mapper
public interface AdminMapper {
    // signin
    Optional<Admin> findByUserName(String username);
    Admin findById(long id);

}
