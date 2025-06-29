package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.model.GradeRecord;

public interface GradeRecordDeletage {
    GradeRecord getGradeRecordById(Long id);
    List<GradeRecord> getAllGradeRecords();
    GradeRecord createGradeRecord(GradeRecord gradeRecord);
    GradeRecord updateGradeRecord(Long id, GradeRecord gradeRecord);
    void deleteGradeRecord(Long id);
}
