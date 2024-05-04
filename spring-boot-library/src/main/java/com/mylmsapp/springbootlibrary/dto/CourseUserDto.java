package com.mylmsapp.springbootlibrary.dto;

import com.mylmsapp.springbootlibrary.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseUserDto {
    private Long Id;
    private String courseCode;
    private String courseName;
    private Set<User> enrolledUsers;

}
