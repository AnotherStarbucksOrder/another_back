package com.starbucksorder.another_back.dto.user.response.point;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RespPointDto {

    private Long userId;
    private int totalPoints;
    // private int point;

}
