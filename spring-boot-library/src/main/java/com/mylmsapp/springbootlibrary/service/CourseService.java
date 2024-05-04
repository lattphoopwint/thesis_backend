package com.mylmsapp.springbootlibrary.service;

import com.mylmsapp.springbootlibrary.dao.CourseRepository;
import com.mylmsapp.springbootlibrary.dto.CourseUserDto;
import com.mylmsapp.springbootlibrary.entity.Course;
import com.mylmsapp.springbootlibrary.mapper.CourseDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final CourseDtoConverter courseDtoConverter;

    @Autowired
    public CourseService(CourseRepository courseRepository, CourseDtoConverter courseDtoConverter) {
        this.courseRepository = courseRepository;
        this.courseDtoConverter = courseDtoConverter;
    }


    public List<CourseUserDto> findAllWithEnrolledUsers() {
        List<Course> courses = courseRepository.findAll();
        List<CourseUserDto> courseUserDtos = new ArrayList<>();
        for (Course course: courses){
            CourseUserDto courseUserDto = new CourseUserDto(
                    course.getId(),
                    course.getCourseCode(),
                    course.getCourseName(),
                    course.getEnrolledUsers()
            );
            courseUserDtos.add(courseUserDto);
        }
        courses.forEach(course -> course.getEnrolledUsers().size()); // This triggers the lazy loading of enrolledUsers


        return courseUserDtos;
    }


    public void saveNewCourse(Course newCourse){
        courseRepository.save(newCourse);
    }
}
