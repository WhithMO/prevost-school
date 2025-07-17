package com.prevost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prevost.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

}
