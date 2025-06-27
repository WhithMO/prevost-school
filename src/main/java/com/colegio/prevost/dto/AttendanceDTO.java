package com.colegio.prevost.dto;

import com.colegio.prevost.model.Course;
import com.colegio.prevost.model.Student;
import com.colegio.prevost.model.Worker;

import lombok.Data;

@Data
public class AttendanceDTO {

    private Long id;

    private Student student;

    private Course course;

    private Worker teacher;

    private Boolean present;
}
