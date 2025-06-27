package com.colegio.prevost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colegio.prevost.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
