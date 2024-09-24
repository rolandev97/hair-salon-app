package com.tp.hair_salon_app.security;


import com.tp.hair_salon_app.models.AppUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Getter
public class UserPrincipal implements UserDetails {
    private AppUser appUser;

    public UserPrincipal(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authorities = new SimpleGrantedAuthority(appUser.getRole().name());
        return Arrays.asList(authorities);
    }

    @Override
    public String getPassword() {
        return appUser.getMotDePasse();
    }

    @Override
    public String getUsername() {
        return appUser.getEmail();
    }

//    @Override
//    public boolean isAccountNonExpired() {
//        return appUser.isAccountNonExpired();
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return appUser.isAccountNonLocked();
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return appUser.isCredentialsNonExpired();
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return appUser.isEnabled();
//    }
}
