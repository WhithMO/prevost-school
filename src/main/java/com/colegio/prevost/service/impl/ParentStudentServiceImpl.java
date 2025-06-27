package com.colegio.prevost.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.model.ParentStudent;
import com.colegio.prevost.repository.ParentStudentRepository;
import com.colegio.prevost.service.ParentStudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParentStudentServiceImpl implements ParentStudentService {

    private final ParentStudentRepository repository;

    @Override
    public ParentStudent getParentStudentById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<ParentStudent> getAllParentStudents() {
        return repository.findAll();
    }

    @Override
    public ParentStudent createParentStudent(ParentStudent parentStudent) {
        return repository.save(parentStudent);
    }

    @Override
    public ParentStudent updateParentStudent(Long id, ParentStudent parentStudent) {
       ParentStudent existingParentStudent = repository.findById(id).orElse(null);
       if (existingParentStudent != null) {
           existingParentStudent.setParent(parentStudent.getParent());
           existingParentStudent.setStudent(parentStudent.getStudent());
           existingParentStudent.setRelationship(parentStudent.getRelationship());
           return repository.save(existingParentStudent);
       }
       return null;
    }

    @Override
    public void deleteParentStudent(Long id) {
        repository.deleteById(id);
    }

}
