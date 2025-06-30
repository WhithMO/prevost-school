package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.CourseDTO;

public interface CourseDeletage {
    CourseDTO getCourseById(String id);
    List<CourseDTO> getAllCourses();
    CourseDTO createCourse(CourseDTO course);
    CourseDTO updateCourse(String id, CourseDTO course);
    void deleteCourse(String id);
}
