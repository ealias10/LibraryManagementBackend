package com.example.LibraryManagementSystem.exception;


import com.example.LibraryManagementSystem.utils.Constants;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UserNotFoundException extends LibraryManagementSystemException {
  private static final long serialVersionUID = 30763009752460581L;

  public UserNotFoundException(String name) {
    super(
        "User is not found with username: " + name,
        HttpStatus.NOT_FOUND,
        Constants.USER_NOT_FOUND_ERROR_CODE);
  }

  public UserNotFoundException(UUID id) {
    super(
        "User is not found with  id : " + id,
        HttpStatus.NOT_FOUND,
        Constants.USER_NOT_FOUND_ERROR_CODE);
  }
}
