package com.colegio.prevost.service.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.StudentCourseDTO;
import com.colegio.prevost.model.StudentCourse;
import com.colegio.prevost.repository.StudentCourseRepository;
import com.colegio.prevost.service.delegate.StudentCourseDeletage;
import com.colegio.prevost.util.mapper.CourseMapper;
import com.colegio.prevost.util.mapper.StudentCourseMapper;
import com.colegio.prevost.util.mapper.StudentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentCourseDelegateImpl implements StudentCourseDeletage {

    private final StudentCourseRepository repository;
    private final StudentCourseMapper studentCourseMapper;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;

    @Override
    public StudentCourseDTO getStudentCourseById(Long id) {
        return studentCourseMapper.toDto(repository.findById(id).orElse(null));
    }

    @Override
    public List<StudentCourseDTO> getAllStudentCourses() {
        return repository.findAll().stream()
                .map(studentCourseMapper::toDto)
                .toList();
    }

    @Override
    public StudentCourseDTO createStudentCourse(StudentCourseDTO studentCourse) {
        return studentCourseMapper.toDto(repository.save(studentCourseMapper.toEntity(studentCourse)));
    }

    @Override
    public StudentCourseDTO updateStudentCourse(Long id, StudentCourseDTO studentCourse) {
      StudentCourse existingStudentCourse = repository.findById(id).orElse(null);
      if (existingStudentCourse != null) {
          existingStudentCourse.setStudent(studentMapper.toEntity(studentCourse.getStudent()));
          existingStudentCourse.setCourse(courseMapper.toEntity(studentCourse.getCourse()));
          existingStudentCourse.setStatus(studentCourse.getStatus());
          repository.save(existingStudentCourse);
          return studentCourse;
      }
      return null;
    }

    @Override
    public void deleteStudentCourse(Long id) {
        repository.deleteById(id);
    }

}
