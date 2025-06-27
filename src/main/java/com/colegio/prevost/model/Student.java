package com.colegio.prevost.model;

import java.time.LocalDate;

import com.colegio.prevost.util.enums.GradeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Data
public class Student extends User {

    @Enumerated(EnumType.STRING)
    @Column(name = "grade")
    private GradeEnum gradeEnum;

    @Column(name = "admission_date")
    private LocalDate admissionDate;

    @Column(name = "egress_date")
    private LocalDate egressDate;


}
