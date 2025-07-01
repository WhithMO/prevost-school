package com.colegio.prevost.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colegio.prevost.model.GradeRecord;
import com.colegio.prevost.util.enums.EvaluationEnum;

public interface GradeRecordRepository extends JpaRepository<GradeRecord, Long> {
    // Consultar Notas
    // Student
    // Parent
    List<GradeRecord> findByStudentUserIdAndCourseId(Long studentUserId, Long courseId);
    List<GradeRecord> findByStudentUserIdAndCourseIdAndEvaluationTypeAndEvaluationDate(
            Long studentUserId, Long courseId, EvaluationEnum evaluation, LocalDate evaluationDate);
}
