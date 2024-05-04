package com.mylmsapp.springbootlibrary.mapper;

import com.mylmsapp.springbootlibrary.dto.UserDto;
import com.mylmsapp.springbootlibrary.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component
public class UserDtoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public UserDto userToUserDtoConverter(User user){
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    public User userDtoToUserConverter(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        return user;
    }

//    public UserDetails convertUserDtoToUserDetails(UserDto userDto) {
//        // Assuming UserDto contains necessary user information like username and authorities
//        return User.builder()
//                .username(userDto.getUsername())
//                // Add other user details as needed, such as password and authorities
//                .build();
//    }

}
