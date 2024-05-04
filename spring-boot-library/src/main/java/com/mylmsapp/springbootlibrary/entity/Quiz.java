package com.mylmsapp.springbootlibrary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Quiz {

    @Id
    @GeneratedValue
    private Long id;
    private Date created_at;
    private Date due_date;
    private Date close_date;

    @ManyToOne
    @JoinTable(
            name = "quiz_course",
            joinColumns = @JoinColumn(name = "quizId"),
            inverseJoinColumns = @JoinColumn(name = "courseId")
    )
    private Course course;


}
