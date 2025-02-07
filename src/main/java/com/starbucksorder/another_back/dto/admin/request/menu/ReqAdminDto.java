package com.starbucksorder.another_back.dto.admin.request.menu;

import com.starbucksorder.another_back.entity.MenuJpaEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

// save
@Data
public class ReqAdminDto {
    @NotBlank(message = "빈값일 수 없습니다")
    private String menuName;
    @NotNull(message = "가격은 빈값일 수 없습니다")
    @Min(value = 0,message = "0원 보다 낮을 수 없습니다")
    private int menuPrice;
    private String comment;
    private Long menuStatus;
    private String imgUrl;

    // 옵션 추가를 위한 것
    private List<Long> optionIds;
    private List<Long> categories;

    // 메뉴 추가 메서드
    public MenuJpaEntity toMenuEntity() {
        return MenuJpaEntity.builder()
                .menuName(menuName)
                .menuPrice(menuPrice)
                .comment(comment)
                .menuStatus(menuStatus != null ? menuStatus : 1L)
                .imgUrl(imgUrl)
                .build();
    }
}