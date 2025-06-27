package com.colegio.prevost.dto;

import lombok.Data;

@Data
public class AttendanceDTO {

    private Long id;

    private StudentDTO student;

    private CourseDTO course;

    private WorkerDTO teacher;

    private Boolean present;
}
