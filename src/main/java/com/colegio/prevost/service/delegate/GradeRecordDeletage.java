package com.colegio.prevost.service.delegate;

import java.time.LocalDate;
import java.util.List;

import com.colegio.prevost.dto.GradeRecordDTO;
import com.colegio.prevost.model.GradeRecord;
import com.colegio.prevost.util.enums.EvaluationEnum;

public interface GradeRecordDeletage {
    GradeRecordDTO getGradeRecordById(String id);
    List<GradeRecordDTO> getAllGradeRecords();
    GradeRecordDTO createGradeRecord(GradeRecordDTO gradeRecord);
    GradeRecordDTO updateGradeRecord(String id, GradeRecordDTO gradeRecord);
    void deleteGradeRecord(String id);

    List<GradeRecordDTO> findByStudentUserIdAndCourseId(Long studentUserId, Long courseId);
    List<GradeRecordDTO> findByStudentUserIdAndCourseIdAndEvaluationTypeAndEvaluationDate(
            Long studentUserId, Long courseId, EvaluationEnum evaluation, LocalDate evaluationDate);
}
