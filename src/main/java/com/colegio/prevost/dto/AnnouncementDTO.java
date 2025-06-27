package com.colegio.prevost.dto;

import com.colegio.prevost.util.enums.GradeEnum;

import lombok.Data;

@Data
public class AnnouncementDTO {

    private Long id;

    private String description;

    private WorkerDTO teacher;

    private GradeEnum grade;
}
