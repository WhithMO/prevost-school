package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.AttendanceDTO;
import com.colegio.prevost.model.Attendance;

public interface AttendanceDeletage {

    AttendanceDTO getAttendanceById(Long id);
    List<AttendanceDTO> getAllAttendances();
    AttendanceDTO createAttendance(AttendanceDTO attendance);
    AttendanceDTO updateAttendance(Long id, AttendanceDTO attendance);
    void deleteAttendance(Long id);
}
