package com.starbucksorder.another_back.dto.user.response.category;


import com.starbucksorder.another_back.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RespCategoryDto {
//    private Long categoryId;
//    private String categoryName;
//    private Long categoryStatus;
//    private Date createDate;
//    private Date updateDate;

    List<Category> categories;
}
