package com.example.LibraryManagementSystem.exception;



import com.example.LibraryManagementSystem.utils.Constants;
import org.springframework.http.HttpStatus;

public class UsersExistsException extends LibraryManagementSystemException {
  private static final long serialVersionUID = 30763009752460581L;

  public UsersExistsException(String name) {

    super(
        "Username  (" + name + ") already exists",
        HttpStatus.CONFLICT,
        Constants.USER_EXISTS_ERROR_CODE,
        "Username  not available",
        "Username  already being used! Please try another");
  }
}
