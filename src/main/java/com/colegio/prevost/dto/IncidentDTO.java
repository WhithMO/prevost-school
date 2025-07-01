package com.colegio.prevost.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class IncidentDTO {

    private Long id;

    private StudentDTO student;

    private WorkerDTO teacher;

    private String description;

    private LocalDate incidentDate;
}
