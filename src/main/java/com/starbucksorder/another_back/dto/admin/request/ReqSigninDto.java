package com.starbucksorder.another_back.dto.admin.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class ReqSigninDto {
    // adminData
    @NotBlank(message = "아이디를 입력해주세요")
    private String username;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
}
