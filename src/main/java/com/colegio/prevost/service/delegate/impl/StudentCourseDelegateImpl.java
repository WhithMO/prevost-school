package com.colegio.prevost.service.delegate.impl;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.StudentCourseDTO;
import com.colegio.prevost.model.StudentCourse;
import com.colegio.prevost.repository.StudentCourseRepository;
import com.colegio.prevost.service.delegate.StudentCourseDeletage;
import com.colegio.prevost.util.mapper.CourseMapper;
import com.colegio.prevost.util.mapper.StudentCourseMapper;
import com.colegio.prevost.util.mapper.StudentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentCourseDelegateImpl implements StudentCourseDeletage {

    private final StudentCourseRepository repository;
    private final StudentCourseMapper studentCourseMapper;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;

    @Override
    public StudentCourseDTO getStudentCourseById(String id) {
        Long convertedId = getConvertedId(id);
        try {
            StudentCourse entity = repository.findById(convertedId)
                    .orElseThrow(() -> new ServiceException("Recurso no encontrado: StudentCourse id=" + id));
            return studentCourseMapper.toDto(entity);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al obtener StudentCourse id={}", id, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public List<StudentCourseDTO> getAllStudentCourses() {
        try {
            return repository.findAll().stream()
                    .map(studentCourseMapper::toDto)
                    .toList();
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al listar StudentCourses", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public StudentCourseDTO createStudentCourse(StudentCourseDTO studentCourse) {
        try {
            return studentCourseMapper.toDto(repository.save(studentCourseMapper.toEntity(studentCourse)));
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al crear StudentCourse", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public StudentCourseDTO updateStudentCourse(String id, StudentCourseDTO studentCourse) {
        try {
            Long convertedId = getConvertedId(id);
            StudentCourse existingStudentCourse = repository.findById(convertedId).orElse(null);
            if (existingStudentCourse == null) {
                throw new ServiceException("Recurso no encontrado: StudentCourse id=" + id);
            }
            existingStudentCourse.setStudent(studentMapper.toEntity(studentCourse.getStudent()));
            existingStudentCourse.setCourse(courseMapper.toEntity(studentCourse.getCourse()));
            existingStudentCourse.setStatus(studentCourse.getStatus());
            repository.save(existingStudentCourse);
            return studentCourse;
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al actualizar StudentCourse id={}", id, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }

    }

    @Override
    public void deleteStudentCourse(String id) {
        Long convertedId = getConvertedId(id);
        try {
            if (!repository.existsById(convertedId)) {
                throw new ServiceException("Recurso no encontrado: StudentCourse id=" + id);
            }
            repository.deleteById(convertedId);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al eliminar StudentCourse id={}", id, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    private long getConvertedId(String id) {
        return Long.parseLong(id.substring(13));
    }

}
