package com.colegio.prevost.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.model.Course;
import com.colegio.prevost.repository.CourseRepository;
import com.colegio.prevost.service.CourseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;

    @Override
    public Course getCourseById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Course> getAllCourses() {
        return repository.findAll();
    }

    @Override
    public Course createCourse(Course course) {
        return repository.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        Course existingCourse = repository.findById(id).orElse(null);
        if (existingCourse != null) {
            existingCourse.setName(course.getName());
            existingCourse.setDescription(course.getDescription());
            existingCourse.setTeacher(course.getTeacher());
            existingCourse.setAssistantTeacher(course.getAssistantTeacher());
            existingCourse.setGrade(course.getGrade());
            existingCourse.setAcademicPeriod(course.getAcademicPeriod());
            return repository.save(existingCourse);
        }
        return null;
    }

    @Override
    public void deleteCourse(Long id) {
        repository.deleteById(id);
    }

}
