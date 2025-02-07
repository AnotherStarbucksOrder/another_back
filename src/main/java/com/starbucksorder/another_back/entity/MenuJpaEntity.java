package com.starbucksorder.another_back.entity;

import com.starbucksorder.another_back.dto.admin.request.menu.ReqAdminModifyDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "menu_tb")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MenuJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private int menuPrice;

    private String comment;

    @Column(nullable = false)
    private Long menuStatus;

    @Column(nullable = false)
    private String imgUrl;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    // 메뉴 수정 메서드
    public void updateMenu(ReqAdminModifyDto dto) {
        this.menuName = dto.getMenuName();
        this.menuPrice = dto.getMenuPrice();
        this.comment = dto.getComment();
        this.imgUrl = dto.getImgUrl();
        this.updateDate = LocalDateTime.now();
    }


}
