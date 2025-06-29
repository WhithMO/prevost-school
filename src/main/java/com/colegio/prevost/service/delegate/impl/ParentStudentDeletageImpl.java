package com.colegio.prevost.service.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.ParentStudentDTO;
import com.colegio.prevost.model.ParentStudent;
import com.colegio.prevost.repository.ParentStudentRepository;
import com.colegio.prevost.service.delegate.ParentStudentDeletage;
import com.colegio.prevost.util.mapper.ParentMapper;
import com.colegio.prevost.util.mapper.ParentStudentMapper;
import com.colegio.prevost.util.mapper.StudentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParentStudentDeletageImpl implements ParentStudentDeletage {

    private final ParentStudentRepository repository;
    private final ParentStudentMapper parentStudentMapper;
    private final ParentMapper parentMapper;
    private final StudentMapper studentMapper;

    @Override
    public ParentStudentDTO getParentStudentById(Long id) {
        return parentStudentMapper.toDto(repository.findById(id).orElse(null));
    }

    @Override
    public List<ParentStudentDTO> getAllParentStudents() {
        return repository.findAll().stream()
                .map(parentStudentMapper::toDto)
                .toList();
    }

    @Override
    public ParentStudentDTO createParentStudent(ParentStudentDTO parentStudent) {
        return parentStudentMapper.toDto(repository.save(parentStudentMapper.toEntity(parentStudent)));
    }

    @Override
    public ParentStudentDTO updateParentStudent(Long id, ParentStudentDTO parentStudent) {
       ParentStudent existingParentStudent = repository.findById(id).orElse(null);
       if (existingParentStudent != null) {
           existingParentStudent.setParent(parentMapper.toEntity(parentStudent.getParent()));
           existingParentStudent.setStudent(studentMapper.toEntity(parentStudent.getStudent()));
           existingParentStudent.setRelationship(parentStudent.getRelationship());
           return parentStudentMapper.toDto(repository.save(existingParentStudent));
       }
       return null;
    }

    @Override
    public void deleteParentStudent(Long id) {
        repository.deleteById(id);
    }

}
