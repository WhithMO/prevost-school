package com.colegio.prevost.service.delegate.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.GradeRecordDTO;
import com.colegio.prevost.model.Course;
import com.colegio.prevost.model.GradeRecord;
import com.colegio.prevost.repository.GradeRecordRepository;
import com.colegio.prevost.service.delegate.GradeRecordDeletage;
import com.colegio.prevost.util.enums.EvaluationEnum;
import com.colegio.prevost.util.mapper.GradeRecordMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GradeRecordDeletageImpl implements GradeRecordDeletage {

    private final GradeRecordRepository repository;
    private final GradeRecordMapper mapper;

    @Override
    public GradeRecordDTO getGradeRecordById(String id) {
        Long convertedId = getConvertedId(id);
        try {
            GradeRecord entity = repository.findById(convertedId)
                    .orElseThrow(() -> new Exception("Recurso no encontrado: GradeRecord id=" + id));
            return mapper.toDto(entity);
        } catch (Exception dae) {
            log.error("Error de acceso a datos al obtener GradeRecord id={}", id, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public List<GradeRecordDTO> getAllGradeRecords() {
        try {
            List<GradeRecordDTO> dtos = new ArrayList<>();
            for (GradeRecord gr : repository.findAll()) {
                dtos.add(mapper.toDto(gr));
            }
            return dtos;
        } catch (Exception dae) {
            log.error("Error de acceso a datos al listar GradeRecords", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public GradeRecordDTO createGradeRecord(GradeRecordDTO gradeRecord) {
        try {
            return mapper.toDto(repository.save(mapper.toEntity(gradeRecord)));
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al crear GradeRecord", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public GradeRecordDTO updateGradeRecord(String id, GradeRecordDTO gradeRecord) {
        try {
            GradeRecordDTO existing = getGradeRecordById(id);
            if (existing == null) {
                throw new ServiceException("El GradeRecord no existe");
            }
            existing.setStudent(gradeRecord.getStudent());
            existing.setCourse(gradeRecord.getCourse());
            existing.setTeacher(gradeRecord.getTeacher());
            existing.setEvaluationType(gradeRecord.getEvaluationType());
            existing.setEvaluationDate(gradeRecord.getEvaluationDate());
            existing.setScore(gradeRecord.getScore());
            return mapper.toDto(repository.save(mapper.toEntity(existing)));
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al actualizar GradeRecord id={}", id, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }

    }

    @Override
    public void deleteGradeRecord(String id) {
        Long convertedId = getConvertedId(id);
        try {
            if (!repository.existsById(convertedId)) {
                throw new ServiceException("Recurso no encontrado: GradeRecord id=" + id);
            }
            repository.deleteById(convertedId);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al eliminar GradeRecord id={}", id, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public List<GradeRecordDTO> findByStudentUserIdAndCourseId(Long studentUserId, Long courseId) {
        try {
            return repository.findByStudentUserIdAndCourseId(studentUserId, courseId)
                             .stream()
                             .map(mapper::toDto)
                             .toList();
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al buscar GradeRecords para studentUserId={} courseId={}", studentUserId, courseId, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public List<GradeRecordDTO> findByStudentUserIdAndCourseIdAndEvaluationTypeAndEvaluationDate(Long studentUserId,
                                                                                              Long courseId,
                                                                                              EvaluationEnum evaluation,
                                                                                              LocalDate evaluationDate) {
        try {
            return repository.findByStudentUserIdAndCourseIdAndEvaluationTypeAndEvaluationDate(
                    studentUserId, courseId, evaluation, evaluationDate)
                    .stream()
                    .map(mapper::toDto)
                    .toList();
        }  catch (DataAccessException dae) {
            log.error("Error de acceso a datos al buscar GradeRecords para studentUserId={} courseId={} evaluation={} date={}", studentUserId, courseId, evaluation, evaluationDate, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    private long getConvertedId(String id) {
        return Long.parseLong(id.substring(8));
    }

}
