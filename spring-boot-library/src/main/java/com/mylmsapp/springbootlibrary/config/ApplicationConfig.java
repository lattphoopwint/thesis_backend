package com.mylmsapp.springbootlibrary.config;


import com.mylmsapp.springbootlibrary.dao.UserRepository;
import com.mylmsapp.springbootlibrary.dto.UserDto;
import com.mylmsapp.springbootlibrary.mapper.UserDtoConverter;
import com.mylmsapp.springbootlibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    private final UserService userService;

    private final UserDtoConverter userDtoConverter;

    @Bean //this class represents a bean and bean is always public
    public UserDetailsService userDetailsService(){
        System.out.println("hi");

        return username ->
                userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

    }


    //fetch user detail and encode password etc
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{

        return config.getAuthenticationManager();
    }
}
