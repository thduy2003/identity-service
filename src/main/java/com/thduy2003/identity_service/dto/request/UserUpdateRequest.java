package com.thduy2003.identity_service.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 3, message="USERNAME_INVALID")
    String username;
    @Size(min = 8, message="PASSWORD_INVALID")
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
}
