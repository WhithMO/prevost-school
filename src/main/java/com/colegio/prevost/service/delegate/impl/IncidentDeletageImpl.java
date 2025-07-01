package com.colegio.prevost.service.delegate.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.IncidentDTO;
import com.colegio.prevost.model.Incident;
import com.colegio.prevost.repository.IncidentRepository;
import com.colegio.prevost.service.delegate.IncidentDeletage;
import com.colegio.prevost.util.mapper.IncidentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncidentDeletageImpl implements IncidentDeletage {

    private final IncidentRepository repository;
    private final IncidentMapper mapper;

    @Override
    public IncidentDTO getIncidentById(String id) {
        Long convertedId = getConvertedId(id);
        return mapper.toDto(repository.findById(convertedId).orElse(null));
    }

    @Override
    public List<IncidentDTO> getAllIncidents() {
        List<IncidentDTO> dtos = new ArrayList<>();
        for (Incident incident : repository.findAll()) {
            dtos.add(mapper.toDto(incident));
        }
        return dtos;
    }

    @Override
    public IncidentDTO createIncident(IncidentDTO incident) {
        return mapper.toDto(repository.save(mapper.toEntity(incident)));
    }

    @Override
    public IncidentDTO updateIncident(String id, IncidentDTO incident) {
        IncidentDTO existing = getIncidentById(id);
        if (existing != null) {
            existing.setStudent(incident.getStudent());
            existing.setTeacher(incident.getTeacher());
            existing.setDescription(incident.getDescription());
            return mapper.toDto(repository.save(mapper.toEntity(existing)));
        }
        return null;
    }

    @Override
    public void deleteIncident(String id) {
        Long convertedId = getConvertedId(id);
        repository.deleteById(convertedId);
    }

    @Override
    public List<IncidentDTO> findByStudentUserIdAndIncidentDate(Long studentUserId, LocalDate incidentDate) {
        return repository.findByStudentUserIdAndIncidentDate(studentUserId, incidentDate)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    private static long getConvertedId(String id) {
        return Long.parseLong(id.substring(8));
    }

}
