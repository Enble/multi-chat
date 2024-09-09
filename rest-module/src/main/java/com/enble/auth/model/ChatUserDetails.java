package com.enble.auth.model;

import com.enble.entity.Member;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class ChatUserDetails implements UserDetails {

    private final Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> member.getRole().name());
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !member.isRemoved();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !member.isRemoved();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !member.isRemoved();
    }

    @Override
    public boolean isEnabled() {
        return !member.isRemoved();
    }

}
