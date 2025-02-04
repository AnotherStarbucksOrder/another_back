package com.starbucksorder.another_back.mapper;

import com.starbucksorder.another_back.entity.Menu;
import com.starbucksorder.another_back.entity.Option;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {

    // 카테고리별 메뉴리스트 조회 -> 9개씩 (페이징 처리 위함)
    List<Menu> findAllByStartIndexAndLimit(
            @Param("categoryId") Long categoryId,
            @Param("startIndex") Long startIndex,
            @Param("limit") Long limit
    );

    // 특정 카테고리에 속한 메뉴 개수를 반환하는 메서드 (페이징 처리를 위해 전체 개수를 조회)
    int getCountAllBySearch(Long categoryId);

    // 메뉴 ID로 메뉴 상세 조회
    Menu findByMenuId(Long menuId);

    // 메뉴 이름으로 메뉴 조회
    Menu findByMenuName(@Param("menuName") String menuName);

    // 전체 메뉴 리스트 조회
    List<Menu> getMenuList();

    // 메뉴 검색 결과의 총 개수 조회
    int totalCount(@Param("searchName") String searchName);

    // 관리자 페이지에서 검색어 기반 메뉴 리스트 조회(페이징)
    List<Menu> getMenuListPageByName(Long startIndex, Long limit, String searchName);

    // 전체 옵션 리스트 조회
    List<Option> getOptionList();

    // 관리자용 메뉴 상세보기(menuId 기반)
    Menu menuDetailByMenuId(Long menuId);

    // 여러개 메뉴 ID로 해당하는 메뉴 리스트 조회
    List<Menu> findByMenuIds(@Param("menuIds") List<Long> menuIds);


}
