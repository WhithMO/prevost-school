package com.colegio.prevost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colegio.prevost.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    void deleteByUsername(String username);
}
