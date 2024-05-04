package com.mylmsapp.springbootlibrary.service;

import com.mylmsapp.springbootlibrary.controller.AddUserRequest;
import com.mylmsapp.springbootlibrary.dao.UserRepository;
import com.mylmsapp.springbootlibrary.dto.UserDto;
import com.mylmsapp.springbootlibrary.entity.User;
import com.mylmsapp.springbootlibrary.mapper.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public UserDtoConverter userDtoConverter;

    public List<UserDto> getAllUsers() {
        System.out.println("entered getAllUsers()");
        List<UserDto> userDtoList = new ArrayList<UserDto>();
        for(User user: userRepository.findAll()){

            userDtoList.add( userDtoConverter.userToUserDtoConverter(user));
        }
        return userDtoList;

    }

    public Optional<UserDto> getUserByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return Optional.ofNullable(user.map(userDtoConverter::userToUserDtoConverter)
                .orElse(null));
    };

    public void saveNewUser(User newUser){
        userRepository.save(newUser);
    }


//    public void addNewUser (AddUserRequest request){
//        var user = User.builder()
//                .uniqueCode(request.getUniqueCode())
//                .firstname(request.getFirstname())
//                .lastname(request.getLastname())
//                .email(request.getEmail())
//                .password(request.getPassword())
//                .role(request.getRole())
//                .status(request.getStatus())
//                .build();
//        System.out.println("In addNewUser="+user);
//        saveNewUser(user);
//    }

}
