package com.colegio.prevost.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.model.Student;
import com.colegio.prevost.repository.StudentRepository;
import com.colegio.prevost.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    @Override
    public Student getStudentById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    @Override
    public Student createStudent(Student student) {
        return repository.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
      Student existingStudent = repository.findById(id).orElse(null);
      if (existingStudent != null) {
          existingStudent.setGradeEnum(student.getGradeEnum());
          existingStudent.setAdmissionDate(student.getAdmissionDate());
          existingStudent.setEgressDate(student.getEgressDate());
          return repository.save(existingStudent);
      }
      return null;
    }

    @Override
    public void deleteStudent(Long id) {
        repository.deleteById(id);
    }

}
