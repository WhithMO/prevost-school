package com.colegio.prevost.service;

import java.util.List;

import com.colegio.prevost.model.GradeRecord;

public interface GradeRecordService {
    GradeRecord getGradeRecordById(Long id);
    List<GradeRecord> getAllGradeRecords();
    GradeRecord createGradeRecord(GradeRecord gradeRecord);
    GradeRecord updateGradeRecord(Long id, GradeRecord gradeRecord);
    void deleteGradeRecord(Long id);
}
