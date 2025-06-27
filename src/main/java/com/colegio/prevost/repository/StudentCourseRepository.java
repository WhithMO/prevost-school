package com.colegio.prevost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colegio.prevost.model.StudentCourse;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {

}
