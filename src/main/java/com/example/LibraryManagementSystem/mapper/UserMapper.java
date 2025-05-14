package com.example.LibraryManagementSystem.mapper;


import com.example.LibraryManagementSystem.model.Role;
import com.example.LibraryManagementSystem.model.Users;
import com.example.LibraryManagementSystem.request.UserCreateCustomerRequest;
import com.example.LibraryManagementSystem.request.UserCreateRequest;
import com.example.LibraryManagementSystem.vo.LoginVO;
import com.example.LibraryManagementSystem.vo.UsersVO;

public class UserMapper {
  public static Users createUser(UserCreateRequest request, Role role) {
    return Users.builder()
            .username(request.getUsername())
            .password(request.getPassword())
            .role(role)
            .email(request.getEmail())
            .active(true)
            .build();
  }

  public static Users createUserCustemer(UserCreateCustomerRequest request, Role role) {
    return Users.builder()
            .username(request.getUsername())
            .password(request.getPassword())
            .role(role)
            .email(request.getEmail())
            .active(true)
            .build();
  }



  public static UsersVO createUserVO(Users user) {
    return UsersVO.builder()
        .userId(user.getId())
        .userName(user.getUsername())
        .userRole(user.getRole().getName())
        .email(user.getEmail())
        .build();
  }

  public static LoginVO createLoginVO(String accessToken, String refreshToken) {
    return LoginVO.builder().refreshToken(refreshToken).accessToken(accessToken).build();
  }

  public static LoginVO createLoginVO(String accessToken) {
    return LoginVO.builder().accessToken(accessToken).build();
  }
}
