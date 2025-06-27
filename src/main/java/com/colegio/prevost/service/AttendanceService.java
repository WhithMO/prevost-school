package com.colegio.prevost.service;

import java.util.List;

import com.colegio.prevost.model.Attendance;

public interface AttendanceService {

    Attendance getAttendanceById(Long id);
    List<Attendance> getAllAttendances();
    Attendance createAttendance(Attendance attendance);
    Attendance updateAttendance(Long id, Attendance attendance);
    void deleteAttendance(Long id);
}
