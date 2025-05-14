package com.example.LibraryManagementSystem.service;



import com.example.LibraryManagementSystem.dao.RoleDao;
import com.example.LibraryManagementSystem.dao.UserDao;
import com.example.LibraryManagementSystem.exception.AuthenticationFailureException;
import com.example.LibraryManagementSystem.exception.RoleNotFoundException;
import com.example.LibraryManagementSystem.exception.UserNotFoundException;
import com.example.LibraryManagementSystem.exception.UsersExistsException;
import com.example.LibraryManagementSystem.mapper.UserMapper;
import com.example.LibraryManagementSystem.model.Role;
import com.example.LibraryManagementSystem.model.Users;
import com.example.LibraryManagementSystem.request.LoginRequest;
import com.example.LibraryManagementSystem.request.RefreshTokenRequest;
import com.example.LibraryManagementSystem.request.UserCreateCustomerRequest;
import com.example.LibraryManagementSystem.request.UserCreateRequest;
import com.example.LibraryManagementSystem.utils.GenerateJWTToken;
import com.example.LibraryManagementSystem.utils.Utility;
import com.example.LibraryManagementSystem.vo.LoginVO;
import com.example.LibraryManagementSystem.vo.UsersVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@org.springframework.stereotype.Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserService {

  @Autowired
  private UserDao userDao;
  @Autowired
  private RoleDao roleDao;
   @Autowired
   private  PasswordEncoder passwordEncoder;
  @Autowired
  private GenerateJWTToken generateJWTToken;

  @Value("${refresh.token.expiry.minutes}")
  private String refreshTokenExpiry;

  @Value("${access.token.expiry.minutes}")
  private long accessTokenExpiry;

  @Value("${spring.security.jwt.secret}")
  private String jwtSecret;



  public UsersVO createUser(UserCreateRequest request)
      throws RoleNotFoundException, UsersExistsException {
    try {
      Users existingUser = userDao.getUserByName(request.getUsername());
      if (existingUser != null) {
        throw new UsersExistsException(request.getUsername());
      }
      Role role = roleDao.getRoleByName(request.getUserRole());
      if (role == null) {
        throw new RoleNotFoundException(request.getUserRole());
      }
      Users users = UserMapper.createUser(request, role);
      users.setPassword(passwordEncoder.encode(users.getPassword()));
      Users savedUser = userDao.saveUser(users);
      log.info("Create user successfully, Request: {}", request);
      return UserMapper.createUserVO(savedUser);
    } catch (Exception e) {
      log.error("Error while creating user,  Request: {}", request);
      throw e;
    }
  }

  public UsersVO createUserCustomer(UserCreateCustomerRequest request)
          throws RoleNotFoundException, UsersExistsException {
    try {
      Users existingUser = userDao.getUserByName(request.getUsername());
      if (existingUser != null) {
        throw new UsersExistsException(request.getUsername());
      }
      Role role = roleDao.getRoleByName("USER");
      if (role == null) {
        throw new RoleNotFoundException("USER");
      }
      Users users = UserMapper.createUserCustemer(request, role);
      users.setPassword(passwordEncoder.encode(users.getPassword()));
      Users savedUser = userDao.saveUser(users);
      log.info("Create user successfully, Request: {}", request);
      return UserMapper.createUserVO(savedUser);
    } catch (Exception e) {
      log.error("Error while creating user,  Request: {}", request);
      throw e;
    }
  }

  public LoginVO loginUser(LoginRequest loginRequest) throws AuthenticationFailureException {
    try {
      Users users = userDao.getUserByName(loginRequest.getUsername());
      if (users == null
          || !passwordEncoder.matches(loginRequest.getPassword(), users.getPassword())) {
        throw new AuthenticationFailureException();
      }
      String accessToken = getAccessToken(users);
      userDao.saveUser(users);
      log.info("User login successfully, Username: {}", loginRequest.getUsername());
      return UserMapper.createLoginVO(accessToken);
    } catch (Exception e) {
      log.error("Error while login, userName: {}", loginRequest.getUsername());
      throw e;
    }
  }

  public LoginVO refreshToken(RefreshTokenRequest refreshTokenRequest)
          throws UserNotFoundException, AuthenticationFailureException {
    String userIdFromToken = null;
    try {
      userIdFromToken = Utility.getUserId();
      UUID userId = UUID.fromString(userIdFromToken);
      Users existingUser = userDao.getUserById(userId);
      if (existingUser == null) {
        throw new UserNotFoundException(UUID.fromString(userIdFromToken));
      }
      if (existingUser.getRefreshToken() == null) {
        throw new AuthenticationFailureException();
      } else if (Boolean.FALSE.equals(
              Utility.validateToken(refreshTokenRequest.getRefreshToken(), jwtSecret))
              || !existingUser.getRefreshToken().equals(refreshTokenRequest.getRefreshToken())) {
        existingUser.setRefreshToken(null);
        userDao.saveUser(existingUser);
        throw new AuthenticationFailureException();
      }
      String accessToken = getAccessToken(existingUser);
      String refreshToken = getRefreshToken();
      log.info("User token successfully refreshed, userId: {}", userId);
      existingUser.setRefreshToken(refreshToken);
      userDao.saveUser(existingUser);
      return UserMapper.createLoginVO(accessToken, refreshToken);
    } catch (Exception e) {
      log.error("Error while refresh token, userId: {}", userIdFromToken);
      throw e;
    }
  }

  private String getRefreshToken() {
    Map<String, Object> claims = new HashMap<>();
    return generateJWTToken.createJWTToken(Long.parseLong(refreshTokenExpiry), claims);
  }


  private String getAccessToken(Users user) {
    Map<String, Object> claims = new HashMap<>();
    String role = user.getRole().getName();
    claims.put("sub", user.getId().toString());
    claims.put("name", user.getUsername());
    claims.put("role", role);
    return generateJWTToken.createJWTToken(accessTokenExpiry, claims);
  }
}
