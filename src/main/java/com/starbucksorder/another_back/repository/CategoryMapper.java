package com.starbucksorder.another_back.repository;

import com.starbucksorder.another_back.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    // FIXME: 해당코드는 menu_category_tb에 각 메뉴아이디를 이용하여 저장하는 save임으로 경로 조정 논의
    int saveByMenuId(Long menuId, List<Long> categories);

    // 카테고리 추가
    int save(Category category);
    // 카테고리 삭제
    int delete(Long categoryId);
    // 카테고리 수정
    int update(Category category);
    //카테고리 상태 수정
    int updateStatus(Long categoryId);

    // 상태코드 1번인 것만의 카테고리를 전부 다 가지고오는 것
    List<Category> findAllByEnable();

    // 상태코드와 상관없이 전부 다 가지고오는 것
    List<Category> FindAll();

    int deleteCategoryById(Long menuId);

    Category findByCategoryName(String categoryName);
}
