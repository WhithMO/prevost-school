package com.colegio.prevost.service.delegate.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.AttendanceDTO;
import com.colegio.prevost.model.Attendance;
import com.colegio.prevost.repository.AttendanceRepository;
import com.colegio.prevost.service.delegate.AttendanceDeletage;
import com.colegio.prevost.util.mapper.AttendanceMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceDeletageImpl implements AttendanceDeletage {

    private final AttendanceRepository repository;
    private final AttendanceMapper mapper;

    @Override
    public AttendanceDTO getAttendanceById(String id) {
        Long convertedId = getConvertedId(id);
        try {
            Attendance entity = repository.findById(convertedId)
                    .orElseThrow(() -> new ServiceException("Recurso no encontrado: Attendance id=" + id));
            return mapper.toDto(entity);
        } catch (Exception dae) {
            log.error("Error de acceso a datos al obtener Attendance id={}", id, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public List<AttendanceDTO> getAllAttendances() {
        try {
            List<AttendanceDTO> attendances = new ArrayList<>();
            for (Attendance attendance : repository.findAll()) {
                attendances.add(mapper.toDto(attendance));
            }
            return attendances;
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al listar Attendance", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public AttendanceDTO createAttendance(AttendanceDTO attendance) {
        try {
            return mapper.toDto(repository.save(mapper.toEntity(attendance)));
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al crear Attendance", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public AttendanceDTO updateAttendance(String id, AttendanceDTO attendance) {
        try {
            AttendanceDTO existingAttendance = getAttendanceById(id);
            if (existingAttendance == null) {
                throw new ServiceException("El anuncio no existe");
            }
            existingAttendance.setStudent(attendance.getStudent());
            existingAttendance.setCourse(attendance.getCourse());
            existingAttendance.setTeacher(attendance.getTeacher());
            existingAttendance.setPresent(attendance.getPresent());
            return mapper.toDto(repository.save(mapper.toEntity(existingAttendance)));
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al actualizar Attendance id={}", id, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public void deleteAttendance(String id) {
        try {
            Long convertedId = getConvertedId(id);
            if (!repository.existsById(convertedId)) {
                throw new ServiceException("Recurso no encontrado: Attendance id=" + id);
            }
            repository.deleteById(convertedId);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al eliminar Attendance id={}", id, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public List<AttendanceDTO> findByStudentUserIdAndCourseId(Long studentUserId, Long courseId) {
        try {
            return repository.findByStudentUserIdAndCourseId(studentUserId, courseId)
                             .stream()
                             .map(mapper::toDto)
                             .toList();
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al buscar Attendance para studentUserId={} courseId={}", studentUserId, courseId, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public List<AttendanceDTO> findByStudentUserIdAndCourseIdAndAttendanceDate(Long studentUserId,
                                                                            Long courseId,
                                                                            LocalDate attendanceDate) {
        try {
            return repository.findByStudentUserIdAndCourseIdAndAttendanceDate(studentUserId, courseId, attendanceDate)
                             .stream()
                             .map(mapper::toDto)
                             .toList();
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al buscar Attendance para studentUserId={} courseId={} date={}", studentUserId, courseId, attendanceDate, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    private Long getConvertedId(String id) {
        return Long.parseLong(id.substring(8));
    }

}
