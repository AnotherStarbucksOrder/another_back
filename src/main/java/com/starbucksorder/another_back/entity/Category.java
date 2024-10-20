package com.starbucksorder.another_back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.starbucksorder.another_back.dto.admin.MenuDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {
    private Long categoryId;
    private String categoryName;
    private Long categoryStatus;
    @JsonIgnore
    private Date createDate;
    @JsonIgnore
    private Date updateDate;
    private int categorySeq;

    private List<Menu> menuList;

    // 관리자 메뉴추가 -> 카테고리 조회
    public MenuDto.RespCategories toCategoryDto() {
        return MenuDto.RespCategories.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .build();
    }

}
