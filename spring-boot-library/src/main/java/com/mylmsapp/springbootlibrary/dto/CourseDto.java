package com.mylmsapp.springbootlibrary.dto;

import com.mylmsapp.springbootlibrary.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private Long Id;
    private String courseCode;
    private String courseName;
    private Set<User> enrolledUsers;

}
