package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.StudentDTO;
import com.colegio.prevost.model.Student;
import com.colegio.prevost.model.User;

public interface StudentDeletage {
    StudentDTO getStudentById(Long id);
    List<StudentDTO> getAllStudents();
    StudentDTO createStudent(StudentDTO student);
    StudentDTO updateStudent(Long id, StudentDTO student);
    void deleteStudent(Long id);
}
