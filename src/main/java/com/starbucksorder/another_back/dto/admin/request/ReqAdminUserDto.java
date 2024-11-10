package com.starbucksorder.another_back.dto.admin.request;

import com.starbucksorder.another_back.entity.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ReqAdminUserDto {
    private Long userId;
    @NotBlank(message = "빈값일 수 없습니다")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다")
    private String phoneNumber;
    private String memo;
    private int starCount;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .phoneNumber(phoneNumber)
                .memo(memo)
                .starCount(starCount)
                .build();
    }
}
