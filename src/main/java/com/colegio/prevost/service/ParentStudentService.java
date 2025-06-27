package com.colegio.prevost.service;

import java.util.List;

import com.colegio.prevost.model.ParentStudent;

public interface ParentStudentService {
    ParentStudent getParentStudentById(Long id);
    List<ParentStudent> getAllParentStudents();
    ParentStudent createParentStudent(ParentStudent parentStudent);
    ParentStudent updateParentStudent(Long id, ParentStudent parentStudent);
    void deleteParentStudent(Long id);
}
