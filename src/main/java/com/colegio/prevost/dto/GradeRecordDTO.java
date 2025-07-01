package com.colegio.prevost.dto;

import java.time.LocalDate;

import com.colegio.prevost.util.enums.EvaluationEnum;

import lombok.Data;

@Data
public class GradeRecordDTO {

    private Long id;

    private StudentDTO student;

    private CourseDTO course;

    private WorkerDTO teacher;

    private EvaluationEnum evaluationType;

    private LocalDate evaluationDate;

    private Double score;
}
