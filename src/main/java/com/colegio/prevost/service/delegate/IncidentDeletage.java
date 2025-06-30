package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.IncidentDTO;

public interface IncidentDeletage {
    IncidentDTO getIncidentById(String id);
    List<IncidentDTO> getAllIncidents();
    IncidentDTO createIncident(IncidentDTO incident);
    IncidentDTO updateIncident(String id, IncidentDTO incident);
    void deleteIncident(String id);
}
