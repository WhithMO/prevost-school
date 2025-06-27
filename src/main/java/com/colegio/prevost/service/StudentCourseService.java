package com.colegio.prevost.service;

import java.util.List;

import com.colegio.prevost.model.StudentCourse;

public interface StudentCourseService {
    StudentCourse getStudentCourseById(Long id);
    List<StudentCourse> getAllStudentCourses();
    StudentCourse createStudentCourse(StudentCourse studentCourse);
    StudentCourse updateStudentCourse(Long id, StudentCourse studentCourse);
    void deleteStudentCourse(Long id);
}
