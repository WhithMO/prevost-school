package com.colegio.prevost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colegio.prevost.model.Parent;

public interface ParentRepository extends JpaRepository<Parent, Long> {

    Parent findByUserUsername(String username);
    Parent deleteByUserUsername(String username);
}
