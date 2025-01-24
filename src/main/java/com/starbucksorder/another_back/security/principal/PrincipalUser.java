package com.starbucksorder.another_back.security.principal;

import com.starbucksorder.another_back.entity.UserRoles;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class PrincipalUser implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private Set<UserRoles> userRoles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한 로직구현
        return userRoles.stream().map(ur -> new SimpleGrantedAuthority(ur.getRole().getName())).collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
