package com.starbucksorder.another_back.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.starbucksorder.another_back.dto.admin.response.category.RespAdminCategoryDto;
import com.starbucksorder.another_back.dto.admin.response.menu.RespAdminCategories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private Long categoryId;
    private String categoryName;
    private Long categoryStatus;

    @JsonIgnore
    private LocalDateTime createDate;

    @JsonIgnore
    private LocalDateTime updateDate;

    private int categorySeq;
    private List<Menu> menuList;

    // 관리자 메뉴추가 -> 카테고리 조회
    public RespAdminCategories toCategoryDto() {
        return RespAdminCategories.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .build();
    }

    public RespAdminCategoryDto toCategories() {
        return RespAdminCategoryDto.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .categoryStatus(categoryStatus)
                .categorySeq(categorySeq)
                .build();
    }


}
