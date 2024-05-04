package com.mylmsapp.springbootlibrary.controller;


import com.mylmsapp.springbootlibrary.dao.CourseRepository;
import com.mylmsapp.springbootlibrary.dao.UserRepository;
import com.mylmsapp.springbootlibrary.dto.CourseDto;
import com.mylmsapp.springbootlibrary.dto.CourseUserDto;
import com.mylmsapp.springbootlibrary.entity.Course;
import com.mylmsapp.springbootlibrary.entity.Quiz;
import com.mylmsapp.springbootlibrary.entity.User;
import com.mylmsapp.springbootlibrary.service.CourseService;
import com.mylmsapp.springbootlibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseService courseService;


//    @Autowired
//    private QuizRepository quizRepository;

    @PostMapping("/course") //posting the data on the given path
    public ResponseEntity<String> addNewCourse(@RequestBody Course newCourse){

        courseService.saveNewCourse(newCourse);
        return new ResponseEntity<String>("Course Added Successfully!", HttpStatus.OK);
    }

    @GetMapping("/courses")
    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

//    @GetMapping("/courses") //get only course list
//    List<CourseProjection> getAllCourses(){
//        return courseRepository.findAllOnlyCourses();
//    }

    @PutMapping("/courses/{courseId}/users/{userId}")
    public ResponseEntity<String> enrollUsertoCourse(
            @PathVariable Long courseId,
            @PathVariable Long userId
    ){
        Course  course = courseRepository.findById(courseId).get();
//        List<Course> ls = courseRepository.custom();
//        System.out.println("JEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEe");
//        System.out.println(ls);

        System.out.println(course);
        User user = userRepository.findById(userId).get();
        System.out.println(user);
        course.enrollUser(user);
        System.out.println(course.getEnrolledUsers());
        courseService.saveNewCourse(course);
        System.out.println("After saving course: "+ courseRepository.findAll());
        System.out.println(course);
        return new ResponseEntity<String>("Enrolled User Successfully!", HttpStatus.OK);
    }

    @GetMapping("course/enrolledUsers") //get course list with associate users
    public List<CourseUserDto> getCoursesWithEnrolledUsers() {
        return courseService.findAllWithEnrolledUsers();
    }

//    @PutMapping("/courses/{courseId}/quizzes/{quizId}")
//    Course insertQuiztoCourse(
//            @PathVariable Long courseId,
//            @PathVariable Long quizId
//    ){
//        Course  course = courseRepository.findById(courseId).get();
//        Quiz quiz = quizRepository.findById(quizId).get();
//        course.insertQuiz(quiz);
//        return courseRepository.save(course);
//    }
//
//    @GetMapping("course/{courseId}/quizzes") //get course list with associate quizzes
//    List<Quiz> getCourseWithInsertedQuiz(
//            @PathVariable Long courseId
//    ) {
//        Course  course = courseRepository.findById(courseId).get();
//        return course.getQuizzes();
//    }

}
