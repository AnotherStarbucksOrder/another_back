package com.starbucksorder.another_back.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDetail {
    private Long menuDetailId;
    private Long optionId;
    private Option option; // option : menudetail = many : 1
    private Menu menu;
}