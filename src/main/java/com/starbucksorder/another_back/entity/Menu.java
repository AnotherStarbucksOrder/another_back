package com.starbucksorder.another_back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.starbucksorder.another_back.dto.admin.request.menu.ReqAdminModifyDto;
import com.starbucksorder.another_back.dto.admin.response.menu.MenuAdminDetailRespDto;
import com.starbucksorder.another_back.dto.admin.response.menu.RespAdminMenuList;
import com.starbucksorder.another_back.dto.user.response.menu.RespMenuImgListDto;
import com.starbucksorder.another_back.dto.user.response.menu.RespMenuListDto;
import com.starbucksorder.another_back.dto.user.response.menu.RespOnlyMenuIdAdnName;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "menu_tb")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Column(name = "menu_price", nullable = false)
    private int menuPrice;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "menu_status", nullable = false)
    private Long menuStatus;

    @CreatedDate
    @Column(name = "create_date", updatable = false)
    @JsonIgnore
    private Date createDate;

    @LastModifiedDate
    @Column(name = "update_date")
    @JsonIgnore
    private Date updateDate;

    @Column(name = "img_url")
    private String imgUrl;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    private List<MenuDetail> menuDetails; // menu : menudetail = 1 : many

    private String categories;
    private String options;

    public RespMenuImgListDto toMenuImgListDto() {
        return RespMenuImgListDto.builder()
                .menuId(menuId)
                .imgUrl(imgUrl)
                .build();
    }

    // 메뉴 아이디, 이름만 뿌려주는 메소드
    public RespOnlyMenuIdAdnName toRespOnlyIdAndNameDto() {
        return RespOnlyMenuIdAdnName.builder()
                .menuId(menuId)
                .menuName(menuName)
                .build();
    }

    // 관리자 메뉴페이지
    public RespAdminMenuList toPageMenuList() {
        return RespAdminMenuList.builder()
                .menuId(menuId)
                .menuName(menuName)
                .menuPrice(menuPrice)
                .menuStatus(menuStatus)
                .categories(categories)
                .options(options)
                .build();
    }

    // 관리자 메뉴 상세보기 리턴
    public MenuAdminDetailRespDto toMenuDetail() {
        return MenuAdminDetailRespDto.builder()
                .menuId(menuId)
                .menuName(menuName)
                .menuPrice(menuPrice)
                .comment(comment)
                .menuStatus(menuStatus)
                .imgUrl(imgUrl)
                .options(options)
                .categories(categories)
                .build();
    }

    // 메뉴 업데이트
    public void  updateMenu(ReqAdminModifyDto dto) {
        this.menuName = dto.getMenuName();
        this.menuPrice = dto.getMenuPrice();
        this.comment = dto.getComment();
        this.imgUrl = dto.getImgUrl();
    }


}
