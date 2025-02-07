package com.starbucksorder.another_back.repository;

import com.starbucksorder.another_back.domain.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuCategoryMapper {
    int save(Long categoryId, List<Long> menuIds);
    int deleteByCategoryId(Long categoryId);
    Category findAllByCategoryId(Long categoryId);
}
