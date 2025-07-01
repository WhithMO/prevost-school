package com.colegio.prevost.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colegio.prevost.model.Incident;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

    List<Incident> findByStudentUserIdAndIncidentDate(Long studentUserId, LocalDate incidentDate);
}
