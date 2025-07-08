package com.colegio.prevost.service.delegate.impl;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.ParentStudentDTO;
import com.colegio.prevost.model.ParentStudent;
import com.colegio.prevost.repository.ParentStudentRepository;
import com.colegio.prevost.service.delegate.ParentStudentDeletage;
import com.colegio.prevost.util.mapper.ParentMapper;
import com.colegio.prevost.util.mapper.ParentStudentMapper;
import com.colegio.prevost.util.mapper.StudentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParentStudentDeletageImpl implements ParentStudentDeletage {

    private final ParentStudentRepository repository;
    private final ParentStudentMapper parentStudentMapper;
    private final ParentMapper parentMapper;
    private final StudentMapper studentMapper;

    @Override
    public ParentStudentDTO getParentStudentById(String id) {
        Long convertedId = getConvertedId(id);
        try {
            ParentStudent entity = repository.findById(convertedId)
                    .orElseThrow(() -> new ServiceException("Recurso no encontrado: ParentStudent id=" + id));
            return parentStudentMapper.toDto(entity);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al obtener ParentStudent id={}", id, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public List<ParentStudentDTO> getAllParentStudents() {
        try {
            return repository.findAll().stream()
                    .map(parentStudentMapper::toDto)
                    .toList();
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al listar ParentStudents", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public ParentStudentDTO createParentStudent(ParentStudentDTO parentStudent) {
        try {
            return parentStudentMapper.toDto(repository.save(parentStudentMapper.toEntity(parentStudent)));
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al crear ParentStudent", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public ParentStudentDTO updateParentStudent(String id, ParentStudentDTO parentStudent) {
        try {
            Long convertedId = getConvertedId(id);
            ParentStudent existingParentStudent = repository.findById(convertedId).orElse(null);
            if (existingParentStudent == null) {
                throw new ServiceException("Recurso no encontrado: ParentStudent id=" + id);
            }
            existingParentStudent.setParent(parentMapper.toEntity(parentStudent.getParent()));
            existingParentStudent.setStudent(studentMapper.toEntity(parentStudent.getStudent()));
            existingParentStudent.setRelationship(parentStudent.getRelationship());
            return parentStudentMapper.toDto(repository.save(existingParentStudent));
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al actualizar ParentStudent id={}", id, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }

    }

    @Override
    public void deleteParentStudent(String id) {
        Long convertedId = getConvertedId(id);
        try {
            if (!repository.existsById(convertedId)) {
                throw new ServiceException("Recurso no encontrado: ParentStudent id=" + id);
            }
            repository.deleteById(convertedId);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al eliminar ParentStudent id={}", id, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    private long getConvertedId(String id) {
        return Long.parseLong(id.substring(7));
    }

}
