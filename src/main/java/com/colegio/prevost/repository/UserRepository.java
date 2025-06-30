package com.colegio.prevost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colegio.prevost.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserUsername(String username);
    User deleteByUserUsername(String username);
}
