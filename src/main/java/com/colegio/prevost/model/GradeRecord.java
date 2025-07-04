package com.colegio.prevost.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.colegio.prevost.util.enums.EvaluationEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "grade_records")
@Data
@Builder
public class GradeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Worker teacher;

    @Enumerated(EnumType.STRING)
    @Column(name = "evaluationType")
    private EvaluationEnum evaluationType;

    @Column(name = "evaluation_date")
    private LocalDate evaluationDate;

    private Double score;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.evaluationDate = LocalDate.now();
        this.createdAt = LocalDateTime.now();
    }

    @Transient
    public String getId() {
        return "GRADER-" + id;
    }
}