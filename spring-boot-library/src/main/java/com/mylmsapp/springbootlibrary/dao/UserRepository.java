package com.mylmsapp.springbootlibrary.dao;

import com.mylmsapp.springbootlibrary.entity.Course;
import com.mylmsapp.springbootlibrary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

//    @Query("SELECT u FROM User u JOIN FETCH u.chosenCourses")
//    List<User> findAllWithEnrolledCourses();
//
    Optional<User> findByEmail(String email);
//
//    Optional<Object> findByUsername(String username);
}
