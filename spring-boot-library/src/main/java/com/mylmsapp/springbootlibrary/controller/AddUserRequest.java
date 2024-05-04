package com.mylmsapp.springbootlibrary.controller;

import com.mylmsapp.springbootlibrary.entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddUserRequest {

    private String uniqueCode;
    private String firstname;
    private String lastname;

    private Role role;

    private String status;
    private String email;
    private String password;
}
