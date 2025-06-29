package com.colegio.prevost.service.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.model.Attendance;
import com.colegio.prevost.repository.AttendanceRepository;
import com.colegio.prevost.service.delegate.AttendanceDeletage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceDeletageImpl implements AttendanceDeletage {

    private final AttendanceRepository repository;

    @Override
    public Attendance getAttendanceById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Attendance> getAllAttendances() {
        return repository.findAll();
    }

    @Override
    public Attendance createAttendance(Attendance attendance) {
        return repository.save(attendance);
    }

    @Override
    public Attendance updateAttendance(Long id, Attendance attendance) {
         Attendance existingAttendance = getAttendanceById(id);
         if (existingAttendance != null) {
             existingAttendance.setStudent(attendance.getStudent());
             existingAttendance.setCourse(attendance.getCourse());
             existingAttendance.setTeacher(attendance.getTeacher());
             existingAttendance.setPresent(attendance.getPresent());
             return repository.save(existingAttendance);
         }
         return null;
    }

    @Override
    public void deleteAttendance(Long id) {
        repository.deleteById(id);
    }

}
