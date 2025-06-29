package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.ParentStudentDTO;
import com.colegio.prevost.model.ParentStudent;

public interface ParentStudentDeletage {
    ParentStudentDTO getParentStudentById(Long id);
    List<ParentStudentDTO> getAllParentStudents();
    ParentStudentDTO createParentStudent(ParentStudentDTO parentStudent);
    ParentStudentDTO updateParentStudent(Long id, ParentStudentDTO parentStudent);
    void deleteParentStudent(Long id);
}
