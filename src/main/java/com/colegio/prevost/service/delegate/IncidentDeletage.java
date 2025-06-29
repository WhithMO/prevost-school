package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.IncidentDTO;

public interface IncidentDeletage {
    IncidentDTO getIncidentById(Long id);
    List<IncidentDTO> getAllIncidents();
    IncidentDTO createIncident(IncidentDTO incident);
    IncidentDTO updateIncident(Long id, IncidentDTO incident);
    void deleteIncident(Long id);
}
