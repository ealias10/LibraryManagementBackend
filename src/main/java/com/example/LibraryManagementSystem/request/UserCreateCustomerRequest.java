package com.example.LibraryManagementSystem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserCreateCustomerRequest {
    @NotEmpty
    @JsonProperty(value = "username")
    private String username;

    @NotEmpty
    @JsonProperty(value = "password")
    private String password;


    @Email
    @NotEmpty private String email;
}
