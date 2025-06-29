package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.model.Course;

public interface CourseDeletage {
    Course getCourseById(Long id);
    List<Course> getAllCourses();
    Course createCourse(Course course);
    Course updateCourse(Long id, Course course);
    void deleteCourse(Long id);
}
