package com.mylmsapp.springbootlibrary.dto;

import com.mylmsapp.springbootlibrary.entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String uniqueCode;

    private String username;
    private String firstname;
    private String lastname;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String status;
    private String email;
    private String password;



}
