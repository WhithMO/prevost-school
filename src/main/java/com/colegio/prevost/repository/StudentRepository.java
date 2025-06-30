package com.colegio.prevost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colegio.prevost.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByUserUsername(String username);
    Student deleteByUserUsername(String username);
}
