package com.starbucksorder.another_back.dto.admin.request.category;

import com.starbucksorder.another_back.entity.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReqAdminCategoryDto {
    private Long categoryId;
    @NotBlank (message = "카테고리 이름은 빈값일 수 없습니다")
    private String categoryName;
    private Long categoryStatus;
    private int categorySeq;

    public Category toEntity() {
        return Category.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .categoryStatus(categoryStatus)
                .categorySeq(categorySeq)
                .build();
    }
}