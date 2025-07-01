package com.colegio.prevost.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AttendanceDTO {

    private Long id;

    private StudentDTO student;

    private CourseDTO course;

    private WorkerDTO teacher;

    private Boolean present;

    private LocalDate attendanceDate;
}
