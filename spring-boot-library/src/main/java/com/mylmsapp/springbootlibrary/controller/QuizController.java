package com.mylmsapp.springbootlibrary.controller;

import com.mylmsapp.springbootlibrary.entity.Course;
import com.mylmsapp.springbootlibrary.entity.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuizController {

//    @Autowired
//    private QuizRepository quizRepository;

//    @PostMapping("/quiz") //posting the data on the given path
//    Quiz newQuiz(@RequestBody Quiz newQuiz){
//        return quizRepository.save(newQuiz);
//    }
//
//    @GetMapping("/quizzes") //get only quiz list
//    List<QuizProjection> getAllQuizzes(){
//        return quizRepository.findAllOnlyQuizzes();
//    }

}
