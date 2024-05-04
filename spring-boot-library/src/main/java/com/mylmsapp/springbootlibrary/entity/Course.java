package com.mylmsapp.springbootlibrary.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

//import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_COURSE")
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    private String courseCode;
    private String courseName;
//    private String semester;
//    private LocalDateTime createdAt;
//    private LocalDateTime deletedAt;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "T_COURSE_USER",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
//    @ManyToMany(mappedBy = "chosenCourses")

    private Set<User> enrolledUsers = new HashSet<>();


    public void enrollUser(User user) {
        System.out.println("Entered to enrollUser from course entity: ");
        enrolledUsers.add(user);
        System.out.println(enrolledUsers);
    }

//    @OneToMany
//    @JoinTable(
//            name = "quiz_course",
//            joinColumns = @JoinColumn(name = "quizId"),
//            inverseJoinColumns = @JoinColumn(name = "courseId")
//    )
//    List<Quiz> quizzes;
//
//    public void insertQuiz(Quiz quiz) {
//        quizzes.add(quiz);
//    }
}

