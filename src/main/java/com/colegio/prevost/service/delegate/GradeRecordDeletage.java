package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.GradeRecordDTO;

public interface GradeRecordDeletage {
    GradeRecordDTO getGradeRecordById(String id);
    List<GradeRecordDTO> getAllGradeRecords();
    GradeRecordDTO createGradeRecord(GradeRecordDTO gradeRecord);
    GradeRecordDTO updateGradeRecord(String id, GradeRecordDTO gradeRecord);
    void deleteGradeRecord(String id);
}
