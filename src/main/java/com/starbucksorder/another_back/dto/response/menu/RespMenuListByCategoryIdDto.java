package com.starbucksorder.another_back.dto.response.menu;

import com.starbucksorder.another_back.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespMenuListByCategoryIdDto {

//    private Long menuId;
//    private Long categoryId;
//    private String menuName;
//    private int menuPrice;
//    private String imgUrl;

    List<Menu> menus;
    private Integer totalCount;

}
