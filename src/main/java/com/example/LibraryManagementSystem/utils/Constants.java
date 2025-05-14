package com.example.LibraryManagementSystem.utils;

public class Constants {
  private Constants() {
    throw new IllegalStateException("Constants class");
  }



  // custom 401 errors
  public static final String UNAUTHORIZED_ERROR_CODE = "401-100";
  public static final String INVALID_USER_CREDENTIALS = "401-101";
  public static final String INVALID_TOKEN_ERROR_CODE = "401-102";




  // custom 404
  public static final String USER_NOT_FOUND_ERROR_CODE = "404-100";
  public static final String ROLE_NOT_FOUND_ERROR_CODE = "404-101";
  public static final String EXPIRED_TOKEN_ERROR_CODE = "404-102";




  // custom 409
  public static final String USER_EXISTS_ERROR_CODE = "409-100";

}
