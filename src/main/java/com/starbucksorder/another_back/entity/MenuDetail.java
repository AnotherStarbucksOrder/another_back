package com.starbucksorder.another_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu_detail_tb")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class MenuDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuDetailId;

    private Long optionId;

    private Option option; // option : menudetail = many : 1

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;
}