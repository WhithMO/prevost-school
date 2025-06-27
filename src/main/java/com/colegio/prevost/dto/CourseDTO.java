package com.colegio.prevost.dto;

import com.colegio.prevost.model.Worker;
import com.colegio.prevost.util.enums.GradeEnum;

public class CourseDTO {

    private Long id;

    private String name;

    private String description;

    private Worker teacher;

    private Worker assistantTeacher;

    private GradeEnum grade;

    private String academicPeriod;
}
