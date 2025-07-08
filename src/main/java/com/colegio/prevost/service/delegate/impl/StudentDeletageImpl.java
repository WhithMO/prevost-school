package com.colegio.prevost.service.delegate.impl;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.StudentDTO;
import com.colegio.prevost.model.Student;
import com.colegio.prevost.model.User;
import com.colegio.prevost.repository.StudentRepository;
import com.colegio.prevost.repository.UserRepository;
import com.colegio.prevost.service.delegate.StudentDeletage;
import com.colegio.prevost.util.mapper.StudentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentDeletageImpl implements StudentDeletage {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final StudentMapper mapper;

    @Override
    public StudentDTO getStudentByUsername(String username) {
        try {
            Student student = studentRepository.findByUserUsername(username);
            if (student == null) {
                throw new ServiceException("Recurso no encontrado: Student username=" + username);
            }
            User user = userRepository.findById(student.getUserId())
                    .orElseThrow(() -> new ServiceException("Recurso no encontrado: User id=" + student.getUserId()));
            return new StudentDTO().getStudentDTO(student, user);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al obtener Student username={}", username, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        try {
            return studentRepository.findAll().stream()
                    .map(student -> getStudentByUsername(student.getUser().getUsername()))
                    .toList();
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al listar Students", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public StudentDTO createStudent(StudentDTO student) {
        try {
            String contructedId = String.format("%s-%s", student.getSurNames()
                    .toUpperCase().charAt(0), student.getDocumentNumber());
            student.setUsername(contructedId);
            User user = userRepository.save(new User().getUserFromDto(student));
            Student studentEntity = studentRepository.save(new Student(
                    user,
                    student.getGradeEnum(),
                    student.getAdmissionDate(),
                    student.getEgressDate()
            ));
            return mapper.toDto(studentEntity);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al crear Student", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public StudentDTO updateStudent(String username, StudentDTO student) {
        try {
            Student existingStudent = studentRepository.findByUserUsername(username);
            if (existingStudent == null) {
                throw new ServiceException("Recurso no encontrado: Student username=" + username);
            }
            User existingUser = userRepository.findById(existingStudent.getUserId())
                    .orElseThrow(() -> new ServiceException("Recurso no encontrado: User id=" + existingStudent.getUserId()));
            existingUser.setNames(student.getNames());
            existingUser.setSurNames(student.getSurNames());
            existingUser.setEmail(student.getEmail());
            userRepository.save(existingUser);

            existingStudent.setGradeEnum(student.getGradeEnum());
            existingStudent.setAdmissionDate(student.getAdmissionDate());
            existingStudent.setEgressDate(student.getEgressDate());
            studentRepository.save(existingStudent);
            return student;
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al actualizar Student username={}", username, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public void deleteStudent(String username) {
        try {
            if (studentRepository.findByUserUsername(username) == null) {
                throw new ServiceException("Recurso no encontrado: Student username=" + username);
            }
            studentRepository.deleteByUserUsername(username);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al eliminar Student username={}", username, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

}
