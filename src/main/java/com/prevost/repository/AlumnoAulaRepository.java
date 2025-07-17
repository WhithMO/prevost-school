package com.prevost.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prevost.model.AlumnoAula;

public interface AlumnoAulaRepository extends JpaRepository<AlumnoAula, Long> {

	List<AlumnoAula> findByAula_Idaula(Long idaula);
	
	void deleteByAula_Idaula(Long idaula);
}
