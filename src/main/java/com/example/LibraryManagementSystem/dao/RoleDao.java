package com.example.LibraryManagementSystem.dao;


import com.example.LibraryManagementSystem.model.Role;
import com.example.LibraryManagementSystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository

public class RoleDao {
  @Autowired
  private
  RoleRepository roleRepository;

  public Role getRoleByName(String role) {
    return roleRepository.getRoleByName(role);
  }
}
