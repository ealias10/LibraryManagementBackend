package com.example.LibraryManagementSystem.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

import static com.example.LibraryManagementSystem.utils.Utility.getUserName;


public class AuditAware implements AuditorAware<String> {

  public Optional<String> getCurrentAuditor() {

    return Optional.of(getUserName());
  }
}
