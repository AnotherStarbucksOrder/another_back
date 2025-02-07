package com.starbucksorder.another_back.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.starbucksorder.another_back.security.principal.PrincipalUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    private Long adminId;
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private LocalDateTime registerDate;

    @JsonIgnore
    private LocalDateTime updateDate;
    private Set<UserRoles> userRoles;

    // xml 구현
    // principal 객체로 변환
    public PrincipalUser toPrincipal() {
        return PrincipalUser.builder()
                .id(getAdminId())
                .username(getUsername())
                .password(getPassword())
                .userRoles(getUserRoles())
                .build();
    }
}
