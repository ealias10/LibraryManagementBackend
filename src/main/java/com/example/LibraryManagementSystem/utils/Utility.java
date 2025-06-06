package com.example.LibraryManagementSystem.utils;



import com.example.LibraryManagementSystem.auth.AuthUser;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Random;

public class Utility {

  private static String system = "system";

  private Utility() {
    throw new IllegalStateException("Utility class");
  }

  private static final Random random = new Random();

  public static String convertToSnakeCase(String camelCaseString) {
    return camelCaseString.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
  }

  public static String getUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return system;
    }
    if (authentication.getPrincipal() instanceof String) {
      return (String) authentication.getPrincipal();
    }
    return ((AuthUser) authentication.getPrincipal()).getUserId();
  }

  public static String getUserName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return system;
    }
    if (authentication.getPrincipal() instanceof String) {
      return system;
    }
    String name = ((AuthUser) authentication.getPrincipal()).getName();
    return name;
  }

  public static Boolean validateToken(String token, String jwtSecret) {
    try {
      Jwts.parser().setSigningKey(jwtSecret.getBytes()).setSigningKey(token);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  public static String getUserRole() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return system;
    }
    if (authentication.getPrincipal() instanceof String) {
      return system;
    }
    String name = ((AuthUser) authentication.getPrincipal()).getRole();
    return name;
  }
}
