package com.mylmsapp.springbootlibrary.dao;

import com.mylmsapp.springbootlibrary.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT distinct c from Course c join fetch c.enrolledUsers")
    List<Course>custom();
//    List<Course> findAllWithEnrolledUsers()

    @Query("Select u from User u Join Fetch u.chosenCourses where u.id = ?1")
//    @Query("select c from Course c Join fetch c.enrolledUsers ")
    Set<Course> getCourseProjectionByUser(Long userId);
}
