package com.colegio.prevost.dto;

import com.colegio.prevost.util.enums.RelationshipEnum;

public class ParentStudentDTO {

    private Long id;

    private UserDTO parent;

    private StudentDTO student;

    private RelationshipEnum relationship;
}
