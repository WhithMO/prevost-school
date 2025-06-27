package com.colegio.prevost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colegio.prevost.model.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

}
