package com.starbucksorder.another_back.dto.admin.request.menu;

import com.starbucksorder.another_back.entity.Menu;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ReqAdminModifyDto {
    private Long menuId;
    @NotBlank(message = "메뉴이름은 빈값일 수 없습니다")
    private String menuName;
    @NotBlank(message = "가격은 빈값일 수 없습니다")
    @Min(value = 0,message = "0원 보다 낮을 수 없습니다")
    private Integer menuPrice;
    private String imgUrl;
    private String comment;
    private List<Long> optionIds;
    private List<Long> categoryIds;

    public Menu toEntity() {
        return Menu.builder()
                .menuId(menuId)
                .menuName(menuName)
                .menuPrice(menuPrice)
                .imgUrl(imgUrl)
                .comment(comment)
                .build();
    }
}
