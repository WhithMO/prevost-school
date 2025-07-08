package com.colegio.prevost.service.delegate.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.CourseDTO;
import com.colegio.prevost.model.Course;
import com.colegio.prevost.repository.CourseRepository;
import com.colegio.prevost.service.delegate.CourseDeletage;
import com.colegio.prevost.util.mapper.CourseMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseDeletageImpl implements CourseDeletage {

    private final CourseRepository repository;
    private final CourseMapper mapper;

    @Override
    public CourseDTO getCourseById(String id) {
        Long convertedId = getConvertedId(id);
        try {
            Course entity = repository.findById(convertedId)
                    .orElseThrow(() -> new ServiceException("Recurso no encontrado: Course id=" + id));
            return mapper.toCourseDTO(entity);
        } catch (Exception dae) {
            log.error("Error de acceso a datos al obtener Course id={}", id, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        try {
            List<CourseDTO> courseDTOS = new ArrayList<>();
            for (Course course : repository.findAll()) {
                courseDTOS.add(mapper.toCourseDTO(course));
            }
            return courseDTOS;
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al listar Course", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public CourseDTO createCourse(CourseDTO course) {
        try {
            return mapper.toCourseDTO(repository.save(mapper.toEntity(course)));
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al crear Course", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public CourseDTO updateCourse(String id, CourseDTO course) {
        try {
            CourseDTO existingCourse = getCourseById(id);
            if (existingCourse == null) {
                throw new ServiceException("El Curso no existe");
            }
            existingCourse.setName(course.getName());
            existingCourse.setDescription(course.getDescription());
            existingCourse.setTeacher(course.getTeacher());
            existingCourse.setAssistantTeacher(course.getAssistantTeacher());
            existingCourse.setGrade(course.getGrade());
            existingCourse.setAcademicPeriod(course.getAcademicPeriod());
            return mapper.toCourseDTO(repository.save(mapper.toEntity(existingCourse)));
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al actualizar Course id={}", id, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public void deleteCourse(String id) {
        try {
            Long convertedId = getConvertedId(id);
            if (!repository.existsById(convertedId)) {
                throw new ServiceException("Recurso no encontrado: Course id=" + id);
            }
            repository.deleteById(convertedId);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al eliminar Course id={}", id, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    private long getConvertedId(String id) {
        return Long.parseLong(id.substring(8));
    }

}
