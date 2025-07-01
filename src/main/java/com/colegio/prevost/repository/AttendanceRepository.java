package com.colegio.prevost.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colegio.prevost.model.Attendance;
import com.colegio.prevost.model.GradeRecord;
import com.colegio.prevost.util.enums.EvaluationEnum;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Consultar Asistencia
    // Student
    // Parent
    List<Attendance> findByStudentUserIdAndCourseId(Long studentUserId, Long courseId);
    List<Attendance> findByStudentUserIdAndCourseIdAndAttendanceDate(
            Long studentUserId, Long courseId, LocalDate attendanceDate);
}
