package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.StudentDTO;
import com.colegio.prevost.model.Student;
import com.colegio.prevost.model.User;

public interface StudentDeletage {
    Student getStudentById(Long id);
    List<Student> getAllStudents();
    Student createStudent(Student student);
    StudentDTO updateStudent(Long id, Student student, User user);
    void deleteStudent(Long id);
}
