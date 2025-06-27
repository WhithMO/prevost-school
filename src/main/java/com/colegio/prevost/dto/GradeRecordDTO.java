package com.colegio.prevost.dto;

import com.colegio.prevost.util.enums.EvaluationEnum;

public class GradeRecordDTO {

    private Long id;

    private StudentDTO student;

    private CourseDTO course;

    private WorkerDTO teacher;

    private EvaluationEnum evaluation;

    private Double score;
}
