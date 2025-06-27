package com.colegio.prevost.dto;

import com.colegio.prevost.model.Course;
import com.colegio.prevost.model.Student;
import com.colegio.prevost.util.enums.StudentCourseRelationEnum;

public class StudentCourseDTO {
    private Long id;

    private Student student;

    private Course course;

    private StudentCourseRelationEnum status;
}
