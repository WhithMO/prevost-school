package com.colegio.prevost.dto;

import com.colegio.prevost.util.enums.GradeEnum;

import lombok.Data;

@Data
public class CourseDTO {

    private Long id;

    private String name;

    private String description;

    private WorkerDTO teacher;

    private WorkerDTO assistantTeacher;

    private GradeEnum grade;

    private String academicPeriod;
}
