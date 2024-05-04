package com.mylmsapp.springbootlibrary.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_USER")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    private String uniqueCode;
    private String firstname;
    private String lastname;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String status;
    private String email;
    private String password;

    @JsonIgnore
    @ManyToMany(mappedBy = "enrolledUsers", cascade = CascadeType.ALL)
//    @ManyToMany(fetch = FetchType.LAZY)
//    Set<Course> chosenCourses;
//    @ManyToMany
//    @JoinTable(
//            name = "T_COURSE_USER",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "course_id")
//    )
    private Set<Course> chosenCourses = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//    public Set<CourseProjection> getCourses() {
//        Set<CourseProjection> result = new HashSet<>();
//        Iterator<Course> iter = chosenCourses.iterator();
//        while (iter.hasNext()) {
//            Course c = iter.next();
//            result.add(new CourseProjection() {
//                @Override
//                public Long getId() {
//                    return c.getId();
//                }
//
//                @Override
//                public String getCourseCode() {
//                    return c.getCourseCode();
//                }
//
//                @Override
//                public String getTitle() {
//                    return c.getTitle();
//                }
//
//                @Override
//                public String getInstructor() {
//                    return c.getInstructor();
//                }
//
//                @Override
//                public String getSemester() {
//                    return c.getSemester();
//                }
//            });
//        }
//        return result;
//    }


}

