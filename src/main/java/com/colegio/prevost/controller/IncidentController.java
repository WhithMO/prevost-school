package com.colegio.prevost.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.colegio.prevost.dto.IncidentDTO;
import com.colegio.prevost.service.delegate.IncidentDeletage;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentDeletage incidentDelegate;

    @GetMapping
    public ResponseEntity<List<IncidentDTO>> getAllIncidents() {
        List<IncidentDTO> incidents = incidentDelegate.getAllIncidents();
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentDTO> getIncidentById(@PathVariable String id) {
        IncidentDTO dto = incidentDelegate.getIncidentById(id);
        return dto != null
                ? ResponseEntity.ok(dto)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<IncidentDTO> createIncident(@RequestBody IncidentDTO incident) {
        IncidentDTO created = incidentDelegate.createIncident(incident);
        return ResponseEntity
                .created(URI.create("/api/incidents/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncidentDTO> updateIncident(
            @PathVariable String id,
            @RequestBody IncidentDTO incident) {

        IncidentDTO updated = incidentDelegate.updateIncident(id, incident);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable String id) {
        incidentDelegate.deleteIncident(id);
        return ResponseEntity.noContent().build();
    }
}
