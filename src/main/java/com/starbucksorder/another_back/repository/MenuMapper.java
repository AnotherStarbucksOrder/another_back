package com.starbucksorder.another_back.repository;

import com.starbucksorder.another_back.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {
//    ArrayList<Menu> findAllCategoryItem();

    // 카테고리별 메뉴리스트 -> 아예 전체 조회
//    ArrayList<Menu> findByCategoryId(Long categoryId);

    // 카테고리별 메뉴리스트 -> 12개씩
    List<Menu> findAllByStartIndexAndLimit(
            @Param("categoryId") Long categoryId,
            @Param("startIndex") Long startIndex,
            @Param("limit") Long limit
    );

    int getCountAllBySearch(Long categoryId);


    Menu findByMenuId(Long menuId);

}
