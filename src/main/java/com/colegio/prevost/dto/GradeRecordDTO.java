package com.colegio.prevost.dto;

import com.colegio.prevost.model.Course;
import com.colegio.prevost.model.Student;
import com.colegio.prevost.model.Worker;
import com.colegio.prevost.util.enums.EvaluationEnum;

public class GradeRecordDTO {

    private Long id;

    private Student student;

    private Course course;

    private Worker teacher;

    private EvaluationEnum evaluation;

    private Double score;
}
