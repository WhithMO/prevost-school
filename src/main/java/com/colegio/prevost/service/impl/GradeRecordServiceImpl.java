package com.colegio.prevost.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.model.GradeRecord;
import com.colegio.prevost.repository.GradeRecordRepository;
import com.colegio.prevost.service.GradeRecordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GradeRecordServiceImpl implements GradeRecordService {

    private final GradeRecordRepository repository;

    @Override
    public GradeRecord getGradeRecordById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<GradeRecord> getAllGradeRecords() {
        return repository.findAll();
    }

    @Override
    public GradeRecord createGradeRecord(GradeRecord gradeRecord) {
        return repository.save(gradeRecord);
    }

    @Override
    public GradeRecord updateGradeRecord(Long id, GradeRecord gradeRecord) {
        GradeRecord existingGradeRecord = repository.findById(id).orElse(null);
        if (existingGradeRecord != null) {
            existingGradeRecord.setStudent(gradeRecord.getStudent());
            existingGradeRecord.setCourse(gradeRecord.getCourse());
            existingGradeRecord.setTeacher(gradeRecord.getTeacher());
            existingGradeRecord.setEvaluation(gradeRecord.getEvaluation());
            existingGradeRecord.setScore(gradeRecord.getScore());
            return repository.save(existingGradeRecord);
        }
        return null;
    }

    @Override
    public void deleteGradeRecord(Long id) {
        repository.deleteById(id);
    }

}
