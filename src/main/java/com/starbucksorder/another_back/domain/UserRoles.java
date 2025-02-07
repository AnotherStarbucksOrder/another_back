package com.starbucksorder.another_back.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoles {
    private Long userRoleId;
    private Long userId;
    private Long roleId;
    private Role role;
}
