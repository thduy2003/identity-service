package com.thduy2003.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;
	@Column(name = "username", unique  = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
	String username;
	String password;
	String firstName;
	String lastName;
	LocalDate dob;
	@ManyToMany
	Set<Role> roles;
}
