package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.StudentDTO;

public interface StudentDeletage {
    StudentDTO getStudentByUsername(String username);
    List<StudentDTO> getAllStudents();
    StudentDTO createStudent(StudentDTO student);
    StudentDTO updateStudent(String username, StudentDTO student);
    void deleteStudent(String username);
}
