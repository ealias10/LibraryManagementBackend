package com.example.LibraryManagementSystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Users  extends Audit{
  @Id
  @Column(name = "id", updatable = false, nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "active")
  private Boolean active;

  @Column(name = "email")
  private String email;

  @Column(name = "refresh_token")
  private String refreshToken;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id", referencedColumnName = "id")
  @ToString.Exclude
  private Role role;
}
