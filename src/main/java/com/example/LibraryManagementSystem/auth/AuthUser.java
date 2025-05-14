package com.example.LibraryManagementSystem.auth;


import com.example.LibraryManagementSystem.model.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@Getter
public class AuthUser implements UserDetails {

  private String userId;
  private String role;
  private String name;
  private Collection<? extends GrantedAuthority> authorities;

  private static final long serialVersionUID = 1L;

  public static AuthUser build(String userId, Roles role, String name) {
    var authorities = Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    return new AuthUser(userId, role.name(), name, authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return null;
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
