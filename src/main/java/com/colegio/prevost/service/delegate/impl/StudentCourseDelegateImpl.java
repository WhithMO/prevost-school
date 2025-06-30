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
    public StudentCourseDTO getStudentCourseById(String id) {
        Long convertedId = getConvertedId(id);
        return studentCourseMapper.toDto(repository.findById(convertedId).orElse(null));
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
    public StudentCourseDTO updateStudentCourse(String id, StudentCourseDTO studentCourse) {
        Long convertedId = getConvertedId(id);
      StudentCourse existingStudentCourse = repository.findById(convertedId).orElse(null);
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
    public void deleteStudentCourse(String id) {
        Long convertedId = getConvertedId(id);
        repository.deleteById(convertedId);
    }

    private long getConvertedId(String id) {
        return Long.parseLong(id.substring(13));
    }

}
