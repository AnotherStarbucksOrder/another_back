package com.starbucksorder.another_back.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMenu {
    private Long menu_categoryId;
    private Long menuId;
    private Long categoryId;
}
