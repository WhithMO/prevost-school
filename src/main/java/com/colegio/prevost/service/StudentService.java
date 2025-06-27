package com.colegio.prevost.service;

import java.util.List;

import com.colegio.prevost.model.Student;

public interface StudentService {
    Student getStudentById(Long id);
    List<Student> getAllStudents();
    Student createStudent(Student student);
    Student updateStudent(Long id, Student student);
    void deleteStudent(Long id);
}
