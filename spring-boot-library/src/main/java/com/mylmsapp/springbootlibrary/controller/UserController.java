package com.mylmsapp.springbootlibrary.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mylmsapp.springbootlibrary.dao.CourseRepository;
import com.mylmsapp.springbootlibrary.dao.UserRepository;
import com.mylmsapp.springbootlibrary.dto.UserDto;
import com.mylmsapp.springbootlibrary.entity.Course;
import com.mylmsapp.springbootlibrary.entity.User;
import com.mylmsapp.springbootlibrary.exception.UserNotFoundException;
import com.mylmsapp.springbootlibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("http://localhost:3000")
//@RequestMapping("/api/v1/user_entity")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/secure_endpoint")
    public ResponseEntity<String> sayHello(){
        return new ResponseEntity<String>("Hello from secure endpoint.", HttpStatus.OK);
    }

    @PostMapping("/saveUser") //posting the data on the given path
    public ResponseEntity<String> saveUser(@RequestBody User newUser) {
        System.out.println("from saveUser: "+newUser);
//        userService.addNewUser(newUserRequest);
        userRepository.save(newUser);
        return new ResponseEntity<String>("User Saved", HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users=userRepository.findAll();

        // Check if users is not null and not empty
        if (users != null && !users.isEmpty()) {
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/user/{userId}")
    public User getUserById( @PathVariable Long userId){
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
    }

    @PutMapping("/user/edit/{userId}") //edit user
    public User updateUser( @RequestBody User newUser ,@PathVariable Long userId){
        return userRepository.findById(userId)
                .map(user -> {
                    user.setUniqueCode(newUser.getUniqueCode());
                    user.setFirstname(newUser.getFirstname());
                    user.setLastname(newUser.getLastname());
                    user.setEmail(newUser.getEmail());
                    user.setStatus(newUser.getStatus());
                    return userRepository.save(user);

                }).orElseThrow( () -> new UserNotFoundException(userId) );
    }

    @DeleteMapping("/user/delete/{userId}")
    public ResponseEntity<String> deleteUser ( @PathVariable Long userId){
        if (!userRepository.existsById(userId)){
            throw new UserNotFoundException(userId);
        }
        userRepository.deleteById(userId);
        return new ResponseEntity<String>("User with id"+ userId + "has been deleted.", HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/courses")
    public Set<Course> getEnrolledCourses(
            @PathVariable Long userId
    ){

        return userRepository.findById(userId).get().getChosenCourses(); //handling for cannot find userId
//        return userRepository.findById(userId).get().getCourses(); //handling for cannot find userId
//        return courseRepository.getCourseProjectionByUser(userId); //handling for cannot find userId
    }

}

