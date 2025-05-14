package com.example.LibraryManagementSystem.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Role extends Audit {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @UuidGenerator
  private UUID id;

  @Column(name = "name", unique = true)
  private String name;
}
