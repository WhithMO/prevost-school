package com.colegio.prevost.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.model.StudentCourse;
import com.colegio.prevost.repository.StudentCourseRepository;
import com.colegio.prevost.service.StudentCourseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentCourseServiceImpl implements StudentCourseService {

    private final StudentCourseRepository repository;

    @Override
    public StudentCourse getStudentCourseById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<StudentCourse> getAllStudentCourses() {
        return repository.findAll();
    }

    @Override
    public StudentCourse createStudentCourse(StudentCourse studentCourse) {
        return repository.save(studentCourse);
    }

    @Override
    public StudentCourse updateStudentCourse(Long id, StudentCourse studentCourse) {
      StudentCourse existingStudentCourse = repository.findById(id).orElse(null);
      if (existingStudentCourse != null) {
          existingStudentCourse.setStudent(studentCourse.getStudent());
          existingStudentCourse.setCourse(studentCourse.getCourse());
          existingStudentCourse.setStatus(studentCourse.getStatus());
          return repository.save(existingStudentCourse);
      }
      return null;
    }

    @Override
    public void deleteStudentCourse(Long id) {
        repository.deleteById(id);
    }

}
