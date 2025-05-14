package com.example.LibraryManagementSystem.exception;



import com.example.LibraryManagementSystem.utils.Constants;
import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends LibraryManagementSystemException {
  private static final long serialVersionUID = 30763009752460581L;

  public ExpiredTokenException() {
    super("Token is expired", HttpStatus.UNAUTHORIZED, Constants.EXPIRED_TOKEN_ERROR_CODE);
  }
}
