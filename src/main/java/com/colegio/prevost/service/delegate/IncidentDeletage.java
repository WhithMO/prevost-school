package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.model.Incident;

public interface IncidentDeletage {
    Incident getIncidentById(Long id);
    List<Incident> getAllIncidents();
    Incident createIncident(Incident incident);
    Incident updateIncident(Long id, Incident incident);
    void deleteIncident(Long id);
}
