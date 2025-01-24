package com.starbucksorder.another_back.dto.user.request.menu;

import com.starbucksorder.another_back.entity.Menu;
import lombok.Data;

// 쓰고있는곳이 있나......???????????
@Data
public class ReqMenuDto {

    private Long categoryId;
    private String categoryName;
    private Long status;
    private String createDate;
    private String updateDate;


    public Menu toEntity() {
        return Menu.builder()
                .menuStatus(status)
                .build();
    }
}
