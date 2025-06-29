package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.StudentCourseDTO;
import com.colegio.prevost.model.StudentCourse;

public interface StudentCourseDeletage {
    StudentCourseDTO getStudentCourseById(Long id);
    List<StudentCourseDTO> getAllStudentCourses();
    StudentCourseDTO createStudentCourse(StudentCourseDTO studentCourse);
    StudentCourseDTO updateStudentCourse(Long id, StudentCourseDTO studentCourse);
    void deleteStudentCourse(Long id);
}
