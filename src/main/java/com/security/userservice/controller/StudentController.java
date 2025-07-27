package com.security.userservice.controller;

import com.security.userservice.model.Student;
import com.security.userservice.model.User;
import com.security.userservice.service.UserService;
import com.security.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    UserService userService;

    StudentController(UserService userService) {
        this.userService = userService;
    }

    private List<Student> students = new ArrayList<Student>(List.of(
            new Student(1,"swarna",90),
            new Student(2,"arjun",95)
    ));
    @GetMapping("/students")
    public List<Student> getStudents() {
        return students;
    }

    @GetMapping("csrf")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken)request.getAttribute("_csrf");
    }

//    @PostMapping("/add")
//    public User addStudent(@RequestBody User user) {
//        return userService.registerStudent(user);
//    }
}
