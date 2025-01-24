package com.starbucksorder.another_back.dto.user.request.menu;

import lombok.Data;

// 페이지네이션에 필요한
@Data
public class ReqMenuListDto {
    private Long categoryId;
    private Long page;
    private Long limit;

}
