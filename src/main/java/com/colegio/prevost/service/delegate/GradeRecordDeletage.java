package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.GradeRecordDTO;
import com.colegio.prevost.model.GradeRecord;

public interface GradeRecordDeletage {
    GradeRecordDTO getGradeRecordById(Long id);
    List<GradeRecordDTO> getAllGradeRecords();
    GradeRecordDTO createGradeRecord(GradeRecordDTO gradeRecord);
    GradeRecordDTO updateGradeRecord(Long id, GradeRecordDTO gradeRecord);
    void deleteGradeRecord(Long id);
}
