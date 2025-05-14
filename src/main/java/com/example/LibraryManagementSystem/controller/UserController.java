package com.example.LibraryManagementSystem.controller;



import com.example.LibraryManagementSystem.exception.AuthenticationFailureException;
import com.example.LibraryManagementSystem.exception.RoleNotFoundException;
import com.example.LibraryManagementSystem.exception.UserNotFoundException;
import com.example.LibraryManagementSystem.exception.UsersExistsException;
import com.example.LibraryManagementSystem.request.LoginRequest;
import com.example.LibraryManagementSystem.request.RefreshTokenRequest;
import com.example.LibraryManagementSystem.request.UserCreateCustomerRequest;
import com.example.LibraryManagementSystem.request.UserCreateRequest;
import com.example.LibraryManagementSystem.service.UserService;
import com.example.LibraryManagementSystem.vo.LoginVO;
import com.example.LibraryManagementSystem.vo.ResponseVO;
import com.example.LibraryManagementSystem.vo.UsersVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@Validated
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/create")
  public ResponseEntity<ResponseVO<Object>> createUser(
      @RequestBody(required = true) UserCreateRequest request)
      throws RoleNotFoundException, UsersExistsException {
    ResponseVO<Object> response = new ResponseVO<>();
    UsersVO usersVO = userService.createUser(request);
    response.addData(usersVO);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/customer/create")
  public ResponseEntity<ResponseVO<Object>> createUserCustomer(
          @RequestBody(required = true) UserCreateCustomerRequest request)
          throws RoleNotFoundException, UsersExistsException {
    ResponseVO<Object> response = new ResponseVO<>();
    System.out.println("-----------------------------"+request);
    UsersVO usersVO = userService.createUserCustomer(request);
    response.addData(usersVO);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseVO<Object>> loginUser(
      @RequestBody(required = true) LoginRequest loginRequest)
      throws AuthenticationFailureException {
    ResponseVO<Object> response = new ResponseVO<>();
    LoginVO loginVO = userService.loginUser(loginRequest);
    response.addData(loginVO);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
  @PostMapping("/refresh-token")
  public ResponseEntity<ResponseVO<Object>> refreshToken(
          @RequestBody(required = true) RefreshTokenRequest refreshTokenRequest)
          throws UserNotFoundException, AuthenticationFailureException {
    ResponseVO<Object> response = new ResponseVO<>();
    LoginVO loginVO = userService.refreshToken(refreshTokenRequest);
    response.addData(loginVO);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
