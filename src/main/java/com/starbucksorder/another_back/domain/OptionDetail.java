package com.starbucksorder.another_back.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.starbucksorder.another_back.dto.admin.response.option.RespAdminOptionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionDetail {
    private Long optionDetailId;
    @JsonIgnore
    private Long optionId;

    private String optionDetailValue;
    private int optionDetailPrice;

    public RespAdminOptionDto.RespOptionDetail toDetail() {
        return RespAdminOptionDto.RespOptionDetail.builder()
                .optionDetailId(optionDetailId)
                .optionDetailValue(optionDetailValue)
                .optionDetailPrice(optionDetailPrice)
                .build();
    }




}
