package com.colegio.prevost.dto;

import java.time.LocalDate;

import com.colegio.prevost.model.Student;
import com.colegio.prevost.model.User;
import com.colegio.prevost.util.enums.GradeEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudentDTO extends UserDTO {

    private GradeEnum gradeEnum;

    private LocalDate admissionDate;

    private LocalDate egressDate;

    public StudentDTO getStudentDTO(Student student, User user) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setNames(user.getNames());
        studentDTO.setSurNames(user.getSurNames());
        studentDTO.setEmail(user.getEmail());
        studentDTO.setGradeEnum(student.getGradeEnum());
        studentDTO.setAdmissionDate(student.getAdmissionDate());
        studentDTO.setEgressDate(student.getEgressDate());
        return studentDTO;
    }
}
