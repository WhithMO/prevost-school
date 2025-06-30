package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.StudentCourseDTO;

public interface StudentCourseDeletage {
    StudentCourseDTO getStudentCourseById(String id);
    List<StudentCourseDTO> getAllStudentCourses();
    StudentCourseDTO createStudentCourse(StudentCourseDTO studentCourse);
    StudentCourseDTO updateStudentCourse(String id, StudentCourseDTO studentCourse);
    void deleteStudentCourse(String id);
}
