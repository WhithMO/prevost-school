package com.colegio.prevost.dto;

import java.time.LocalDate;

import com.colegio.prevost.util.enums.GradeEnum;

public class StudentDTO extends UserDTO {

    private GradeEnum gradeEnum;

    private LocalDate admissionDate;

    private LocalDate egressDate;
}
