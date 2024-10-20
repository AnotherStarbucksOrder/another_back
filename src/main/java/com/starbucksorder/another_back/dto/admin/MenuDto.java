package com.starbucksorder.another_back.dto.admin;

import com.starbucksorder.another_back.entity.Menu;
import lombok.Data;

import java.util.List;

public class MenuDto {

    // save
    @Data
    public static class ReqDto {
        private String menuName;
        private int menuPrice;
        private String comment;
        private Long menuStatus;
        private String imgUrl;

        // 옵션 추가를 위한 것
        private List<Long> optionIds;

        public Menu toMenuEntity() {
            return Menu.builder()
                    .menuName(menuName)
                    .menuPrice(menuPrice)
                    .comment(comment)
                    .menuStatus(menuStatus)
                    .imgUrl(imgUrl)
                    .build();
        }
    }
}