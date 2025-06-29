package com.colegio.prevost.dto;

import com.colegio.prevost.util.enums.RelationshipEnum;

import lombok.Data;

@Data
public class ParentStudentDTO {

    private Long id;

    private ParentDTO parent;

    private StudentDTO student;

    private RelationshipEnum relationship;
}
