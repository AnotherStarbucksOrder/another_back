package com.starbucksorder.another_back.dto.admin.request.option;

import com.starbucksorder.another_back.domain.Option;
import com.starbucksorder.another_back.domain.OptionDetail;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ReqAdminOptionDto {
    private Long optionId;
    @NotBlank(message = "옵션이름은 빈값일 수 없습니다")
    private String optionName;
    private Long optionStatus;
    //    private HashMap<String, Integer> optionDetail;
    private List<OptionDetail> optionDetail;

    public Option toEntity() {
        return Option.builder()
                .optionId(optionId)
                .optionName(optionName)
                .optionStatus(optionStatus)
                .build();
    }

    /*public OptionDetail toDetailEntity(Long optionId, String value, int price) {
        return OptionDetail.builder()
                .optionId(optionId)
                .optionDetailValue(value)
                .optionDetailPrice(price)
                .build();
    }*/
    public OptionDetail toDetailEntity(Long optionId, String value, int price) {
        return OptionDetail.builder()
                .optionId(optionId)
                .optionDetailValue(value)
                .optionDetailPrice(price)
                .build();
    }
}
