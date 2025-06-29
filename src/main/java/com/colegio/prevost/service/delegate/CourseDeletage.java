package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.CourseDTO;

public interface CourseDeletage {
    CourseDTO getCourseById(Long id);
    List<CourseDTO> getAllCourses();
    CourseDTO createCourse(CourseDTO course);
    CourseDTO updateCourse(Long id, CourseDTO course);
    void deleteCourse(Long id);
}
