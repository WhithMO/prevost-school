package com.colegio.prevost.dto;

import com.colegio.prevost.model.Student;
import com.colegio.prevost.model.User;
import com.colegio.prevost.util.enums.RelationshipEnum;

public class ParentStudentDTO {

    private Long id;

    private User parent;

    private Student student;

    private RelationshipEnum relationship;
}
