package com.starbucksorder.another_back.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.starbucksorder.another_back.dto.admin.response.menu.RespAdminOptions;
import com.starbucksorder.another_back.dto.admin.response.option.RespAdminOptionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Option {

    private Long optionId;
    private String optionName;
    private Long optionStatus;

    @JsonIgnore
    private LocalDateTime createDate;

    @JsonIgnore
    private LocalDateTime updateDate;

    private List<OptionDetail> optionDetail; // optionDetail : option = many : 1

    public RespAdminOptions toOptionsDto() {
        return RespAdminOptions.builder()
                .optionId(optionId)
                .optionName(optionName)
                .build();
    }

    public RespAdminOptionDto toOptionAllDto() {
        return RespAdminOptionDto.builder()
                .optionId(optionId)
                .optionName(optionName)
                .optionStatus(optionStatus)
//                .optionDetail(optionDetail.stream().map(OptionDetail::toDetail).collect(Collectors.toList()))
                .build();
    }
}
