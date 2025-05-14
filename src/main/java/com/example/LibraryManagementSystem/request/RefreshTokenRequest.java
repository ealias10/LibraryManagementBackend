package com.example.LibraryManagementSystem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RefreshTokenRequest {

  @NotEmpty
  @JsonProperty("refresh_token")
  private String refreshToken;
}
