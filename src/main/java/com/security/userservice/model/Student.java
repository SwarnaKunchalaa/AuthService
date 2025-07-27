package com.security.userservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String name;
    int marks;

    public Student() {}

    public Student(int id, String name, int marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;

    }
}
