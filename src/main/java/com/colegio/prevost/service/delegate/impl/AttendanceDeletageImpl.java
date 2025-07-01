package com.colegio.prevost.service.delegate.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.AttendanceDTO;
import com.colegio.prevost.model.Attendance;
import com.colegio.prevost.repository.AttendanceRepository;
import com.colegio.prevost.service.delegate.AttendanceDeletage;
import com.colegio.prevost.util.mapper.AttendanceMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceDeletageImpl implements AttendanceDeletage {

    private final AttendanceRepository repository;
    private final AttendanceMapper mapper;

    @Override
    public AttendanceDTO getAttendanceById(String id) {
        Long convertedId = getConvertedId(id);
        return mapper.toDto(repository.findById(convertedId).orElse(null));
    }

    @Override
    public List<AttendanceDTO> getAllAttendances() {
        List<AttendanceDTO> attendances = new ArrayList<>();
        for (Attendance attendance : repository.findAll()) {
            attendances.add(mapper.toDto(attendance));
        }
        return attendances;
    }

    @Override
    public AttendanceDTO createAttendance(AttendanceDTO attendance) {
        return mapper.toDto(repository.save(mapper.toEntity(attendance)));
    }

    @Override
    public AttendanceDTO updateAttendance(String id, AttendanceDTO attendance) {
        AttendanceDTO existingAttendance = getAttendanceById(id);
         if (existingAttendance != null) {
             existingAttendance.setStudent(attendance.getStudent());
             existingAttendance.setCourse(attendance.getCourse());
             existingAttendance.setTeacher(attendance.getTeacher());
             existingAttendance.setPresent(attendance.getPresent());
             return mapper.toDto(repository.save(mapper.toEntity(existingAttendance)));
         }
         return null;
    }

    @Override
    public void deleteAttendance(String id) {
        Long convertedId = getConvertedId(id);
        repository.deleteById(convertedId);
    }

    @Override
    public List<AttendanceDTO> findByStudentUserIdAndCourseId(Long studentUserId, Long courseId) {
   return repository.findByStudentUserIdAndCourseId(studentUserId, courseId)
                    .stream()
                    .map(mapper::toDto)
                    .toList();
    }

    @Override
    public List<AttendanceDTO> findByStudentUserIdAndCourseIdAndAttendanceDate(Long studentUserId,
                                                                            Long courseId,
                                                                            LocalDate attendanceDate) {
        return repository.findByStudentUserIdAndCourseIdAndAttendanceDate(studentUserId, courseId, attendanceDate)
                         .stream()
                         .map(mapper::toDto)
                         .toList();
    }

    private Long getConvertedId(String id) {
        return Long.parseLong(id.substring(8));
    }

}
