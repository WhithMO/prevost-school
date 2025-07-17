package com.prevost.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prevost.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByUsername(String username);

}
