package com.prevost.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prevost.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
	
	 Optional<Rol> findByNombre(String nombre);

}
