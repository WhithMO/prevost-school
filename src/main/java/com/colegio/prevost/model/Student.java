package com.colegio.prevost.model;

import java.time.LocalDate;

import com.colegio.prevost.util.enums.GradeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "student_profiles")
@Data
public class Student {

    @Id
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade")
    private GradeEnum gradeEnum;

    @Column(name = "admission_date")
    private LocalDate admissionDate;

    @Column(name = "egress_date")
    private LocalDate egressDate;


}
