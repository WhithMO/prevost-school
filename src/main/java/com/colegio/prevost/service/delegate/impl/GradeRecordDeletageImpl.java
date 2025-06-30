package com.colegio.prevost.service.delegate.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.GradeRecordDTO;
import com.colegio.prevost.model.GradeRecord;
import com.colegio.prevost.repository.GradeRecordRepository;
import com.colegio.prevost.service.delegate.GradeRecordDeletage;
import com.colegio.prevost.util.mapper.GradeRecordMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GradeRecordDeletageImpl implements GradeRecordDeletage {

    private final GradeRecordRepository repository;
    private final GradeRecordMapper mapper;

    @Override
    public GradeRecordDTO getGradeRecordById(String id) {
        Long convertedId = getConvertedId(id);
        return mapper.toDto(repository.findById(convertedId).orElse(null));
    }

    @Override
    public List<GradeRecordDTO> getAllGradeRecords() {
        List<GradeRecordDTO> dtos = new ArrayList<>();
        for (GradeRecord gr : repository.findAll()) {
            dtos.add(mapper.toDto(gr));
        }
        return dtos;
    }

    @Override
    public GradeRecordDTO createGradeRecord(GradeRecordDTO gradeRecord) {
        return mapper.toDto(repository.save(mapper.toEntity(gradeRecord)));
    }

    @Override
    public GradeRecordDTO updateGradeRecord(String id, GradeRecordDTO gradeRecord) {
        GradeRecordDTO existing = getGradeRecordById(id);
        if (existing != null) {
            existing.setStudent(gradeRecord.getStudent());
            existing.setCourse(gradeRecord.getCourse());
            existing.setTeacher(gradeRecord.getTeacher());
            existing.setEvaluation(gradeRecord.getEvaluation());
            existing.setScore(gradeRecord.getScore());
            return mapper.toDto(repository.save(mapper.toEntity(existing)));
        }
        return null;
    }

    @Override
    public void deleteGradeRecord(String id) {
        Long convertedId = getConvertedId(id);
        repository.deleteById(convertedId);
    }

    private static long getConvertedId(String id) {
        return Long.parseLong(id.substring(8));
    }

}
