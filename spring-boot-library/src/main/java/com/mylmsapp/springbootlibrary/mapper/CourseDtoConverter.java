package com.mylmsapp.springbootlibrary.mapper;

import com.mylmsapp.springbootlibrary.dto.CourseDto;
import com.mylmsapp.springbootlibrary.dto.UserDto;
import com.mylmsapp.springbootlibrary.entity.Course;
import com.mylmsapp.springbootlibrary.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseDtoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public CourseDto courseToCourseDtoConverter(Course course){
        CourseDto courseDto = modelMapper.map(course, CourseDto.class);
        return courseDto;
    }

    public Course courseDtoToCourseConverter(CourseDto courseDto){
        Course course = modelMapper.map(courseDto, Course.class);
        return course;
    }
}
