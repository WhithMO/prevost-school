package com.colegio.prevost.service;

import java.util.List;

import com.colegio.prevost.model.Incident;

public interface IncidentService {
    Incident getIncidentById(Long id);
    List<Incident> getAllIncidents();
    Incident createIncident(Incident incident);
    Incident updateIncident(Long id, Incident incident);
    void deleteIncident(Long id);
}
