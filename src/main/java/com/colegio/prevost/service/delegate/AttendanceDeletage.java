package com.colegio.prevost.service.delegate;

import java.time.LocalDate;
import java.util.List;

import com.colegio.prevost.dto.AttendanceDTO;
import com.colegio.prevost.model.Attendance;

public interface AttendanceDeletage {

    AttendanceDTO getAttendanceById(String id);
    List<AttendanceDTO> getAllAttendances();
    AttendanceDTO createAttendance(AttendanceDTO attendance);
    AttendanceDTO updateAttendance(String id, AttendanceDTO attendance);
    void deleteAttendance(String id);

    List<AttendanceDTO> findByStudentUserIdAndCourseId(Long studentUserId, Long courseId);
    List<AttendanceDTO> findByStudentUserIdAndCourseIdAndAttendanceDate(
            Long studentUserId, Long courseId, LocalDate attendanceDate);
}
