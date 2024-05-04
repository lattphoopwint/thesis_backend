package com.mylmsapp.springbootlibrary.controller;

import com.mylmsapp.springbootlibrary.config.JwtService;
import com.mylmsapp.springbootlibrary.dao.UserRepository;
import com.mylmsapp.springbootlibrary.dto.UserDto;
import com.mylmsapp.springbootlibrary.entity.Role;
import com.mylmsapp.springbootlibrary.entity.User;
import com.mylmsapp.springbootlibrary.mapper.UserDtoConverter;
import com.mylmsapp.springbootlibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class AuthenticationService {

    private final UserService userService;
    private final  UserDtoConverter userDtoConverter;
    private final PasswordEncoder passwordEncoder;
    private  final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        System.out.println("In register="+user);
        userService.saveNewUser(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        System.out.println(request.getEmail()+" "+request.getPassword());
        try{
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );}
        catch (AuthenticationException e){
            System.out.println("Authentication failed: " + e.getMessage());
        }

        var user = userService.getUserByEmail(request.getEmail())
                .orElseThrow();
        System.out.println("from signin: " + user);
        UserDetails userDetails = userDtoConverter.userDtoToUserConverter(user);
        System.out.println(userDetails);
        var jwtToken = jwtService.generateToken(userDetails);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


}
