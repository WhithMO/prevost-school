package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.model.ParentStudent;

public interface ParentStudentDeletage {
    ParentStudent getParentStudentById(Long id);
    List<ParentStudent> getAllParentStudents();
    ParentStudent createParentStudent(ParentStudent parentStudent);
    ParentStudent updateParentStudent(Long id, ParentStudent parentStudent);
    void deleteParentStudent(Long id);
}
