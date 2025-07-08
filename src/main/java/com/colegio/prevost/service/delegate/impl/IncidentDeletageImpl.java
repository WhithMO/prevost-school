package com.colegio.prevost.service.delegate.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.IncidentDTO;
import com.colegio.prevost.model.Incident;
import com.colegio.prevost.repository.IncidentRepository;
import com.colegio.prevost.service.delegate.IncidentDeletage;
import com.colegio.prevost.util.mapper.IncidentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncidentDeletageImpl implements IncidentDeletage {

    private final IncidentRepository repository;
    private final IncidentMapper mapper;

    @Override
    public IncidentDTO getIncidentById(String id) {
        Long convertedId = getConvertedId(id);
        try {
            Incident entity = repository.findById(convertedId)
                    .orElseThrow(() -> new ServiceException("Recurso no encontrado: Incident id=" + id));
            return mapper.toDto(entity);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al obtener Incident id={}", id, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public List<IncidentDTO> getAllIncidents() {
        try {
            List<IncidentDTO> dtos = new ArrayList<>();
            for (Incident incident : repository.findAll()) {
                dtos.add(mapper.toDto(incident));
            }
            return dtos;
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al listar Incident", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public IncidentDTO createIncident(IncidentDTO incident) {
        try {
            return mapper.toDto(repository.save(mapper.toEntity(incident)));
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al crear Incident", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public IncidentDTO updateIncident(String id, IncidentDTO incident) {
        try {
            IncidentDTO existing = getIncidentById(id);
            if (existing == null) {
                throw new ServiceException("El Incident no existe");
            }
            existing.setStudent(incident.getStudent());
            existing.setTeacher(incident.getTeacher());
            existing.setDescription(incident.getDescription());
            return mapper.toDto(repository.save(mapper.toEntity(existing)));
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al actualizar Incident id={}", id, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }

    }

    @Override
    public void deleteIncident(String id) {
        Long convertedId = getConvertedId(id);
        try {
            if (!repository.existsById(convertedId)) {
                throw new ServiceException("Recurso no encontrado: Incident id=" + id);
            }
            repository.deleteById(convertedId);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al eliminar Incident id={}", id, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public List<IncidentDTO> findByStudentUserIdAndIncidentDate(Long studentUserId, LocalDate incidentDate) {
        try {
            return repository.findByStudentUserIdAndIncidentDate(studentUserId, incidentDate)
                    .stream()
                    .map(mapper::toDto)
                    .toList();
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al buscar Incident para studentUserId={} date={}", studentUserId, incidentDate, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    private static long getConvertedId(String id) {
        return Long.parseLong(id.substring(8));
    }

}
