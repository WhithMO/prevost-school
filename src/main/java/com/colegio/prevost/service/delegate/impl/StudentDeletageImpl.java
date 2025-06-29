package com.colegio.prevost.service.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.StudentDTO;
import com.colegio.prevost.model.Student;
import com.colegio.prevost.model.User;
import com.colegio.prevost.repository.StudentRepository;
import com.colegio.prevost.repository.UserRepository;
import com.colegio.prevost.service.delegate.StudentDeletage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentDeletageImpl implements StudentDeletage {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public StudentDTO updateStudent(Long id, Student student, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        Student existingStudent = studentRepository.findById(id).orElse(null);

        if (existingUser != null) {
            existingUser.setCode(user.getCode());
            existingUser.setNames(user.getNames());
            existingUser.setSurNames(user.getSurNames());
            existingUser.setEmail(user.getEmail());
            userRepository.save(existingUser);
        }
        if (existingStudent != null) {
            existingStudent.setGradeEnum(student.getGradeEnum());
            existingStudent.setAdmissionDate(student.getAdmissionDate());
            existingStudent.setEgressDate(student.getEgressDate());
            studentRepository.save(existingStudent);
        }
        return new StudentDTO().getStudentDTO(student, user);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

}
