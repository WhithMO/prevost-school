package com.colegio.prevost.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.model.Incident;
import com.colegio.prevost.repository.IncidentRepository;
import com.colegio.prevost.service.IncidentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository repository;

    @Override
    public Incident getIncidentById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Incident> getAllIncidents() {
        return repository.findAll();
    }

    @Override
    public Incident createIncident(Incident incident) {
        return repository.save(incident);
    }

    @Override
    public Incident updateIncident(Long id, Incident incident) {
        Incident existingIncident = repository.findById(id).orElse(null);
        if (existingIncident != null) {
            existingIncident.setStudent(incident.getStudent());
            existingIncident.setTeacher(incident.getTeacher());
            existingIncident.setDescription(incident.getDescription());
            return repository.save(existingIncident);
        }
        return null;
    }

    @Override
    public void deleteIncident(Long id) {
        repository.deleteById(id);
    }

}
