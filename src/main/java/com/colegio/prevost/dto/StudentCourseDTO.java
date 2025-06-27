package com.colegio.prevost.dto;

import com.colegio.prevost.util.enums.StudentCourseRelationEnum;

public class StudentCourseDTO {
    private Long id;

    private StudentDTO student;

    private CourseDTO course;

    private StudentCourseRelationEnum status;
}
