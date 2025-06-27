package com.colegio.prevost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colegio.prevost.model.Incident;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

}
