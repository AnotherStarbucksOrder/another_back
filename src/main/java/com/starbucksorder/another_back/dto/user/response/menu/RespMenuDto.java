package com.starbucksorder.another_back.dto.user.response.menu;

import com.starbucksorder.another_back.domain.MenuDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.List;


@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RespMenuDto {
    private Long menuId;
    private String menuName;
    private String comment;
    private int menuPrice;
    private String imgUrl;

    private List<MenuDetail> menuDetailList;
//    private List<Option> optionList;

//    public RespMenuDto response() {
//        return RespMenuDto.builder()
//                .menuId(menuId)
//                .menuName(menuName)
//                .comment(comment)
//                .menuPrice(menuPrice)
//                .imgUrl(imgUrl)
//                .build();
//    }

    /*
     * menu:{
     * menudId : 1,
     * menuName : 커피,
     * comment : "설명",

     * }
     */


//    Set<Menu> menu;
}
