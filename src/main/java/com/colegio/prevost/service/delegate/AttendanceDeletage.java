package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.model.Attendance;

public interface AttendanceDeletage {

    Attendance getAttendanceById(Long id);
    List<Attendance> getAllAttendances();
    Attendance createAttendance(Attendance attendance);
    Attendance updateAttendance(Long id, Attendance attendance);
    void deleteAttendance(Long id);
}
