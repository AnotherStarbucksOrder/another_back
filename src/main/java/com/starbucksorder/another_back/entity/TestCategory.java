package com.starbucksorder.another_back.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TestCategory {
    private Long categoryId;
    private String categoryName;
    private Long status;
    private String createDate;
    private String updateDate;

    private List<TestMenuList> testMenuList;
}
