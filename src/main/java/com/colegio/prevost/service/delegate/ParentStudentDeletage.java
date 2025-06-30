package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.ParentStudentDTO;

public interface ParentStudentDeletage {
    ParentStudentDTO getParentStudentById(String id);
    List<ParentStudentDTO> getAllParentStudents();
    ParentStudentDTO createParentStudent(ParentStudentDTO parentStudent);
    ParentStudentDTO updateParentStudent(String id, ParentStudentDTO parentStudent);
    void deleteParentStudent(String id);
}
