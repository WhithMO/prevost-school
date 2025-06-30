package com.colegio.prevost.service.delegate.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.StudentDTO;
import com.colegio.prevost.model.Student;
import com.colegio.prevost.model.User;
import com.colegio.prevost.repository.StudentRepository;
import com.colegio.prevost.repository.UserRepository;
import com.colegio.prevost.service.delegate.StudentDeletage;
import com.colegio.prevost.util.mapper.StudentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentDeletageImpl implements StudentDeletage {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final StudentMapper mapper;

    @Override
    public StudentDTO getStudentByUsername(String username) {
        Student student = studentRepository.findByUserUsername(username);
        if (student != null) {
            User user = userRepository.findById(student.getUserId()).orElse(null);
            return new StudentDTO().getStudentDTO(student, user);
        }
        return null;
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(student -> getStudentByUsername(student.getUser().getUsername()))
                .toList();
    }

    @Override
    public StudentDTO createStudent(StudentDTO student) {
        User user = userRepository.save(new User().getUserFromDto(student));
        Student studentEntity = studentRepository.save(new Student(
                user,
                student.getGradeEnum(),
                student.getAdmissionDate(),
                student.getEgressDate()
        ));
        return mapper.toDto(studentEntity);
    }

    @Override
    public StudentDTO updateStudent(String username, StudentDTO student) {
        Student existingStudent = studentRepository.findByUserUsername(username);
        if (existingStudent != null) {
            User existingUser = userRepository.findById(existingStudent.getUserId()).orElse(null);
            existingUser.setNames(student.getNames());
            existingUser.setSurNames(student.getSurNames());
            existingUser.setEmail(student.getEmail());
            userRepository.save(existingUser);

            existingStudent.setGradeEnum(student.getGradeEnum());
            existingStudent.setAdmissionDate(student.getAdmissionDate());
            existingStudent.setEgressDate(student.getEgressDate());
            studentRepository.save(existingStudent);
            return student;
        }
        return null;
    }

    @Override
    public void deleteStudent(String username) {
        studentRepository.deleteByUserUsername(username);
    }

}
