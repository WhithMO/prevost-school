package com.colegio.prevost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colegio.prevost.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

}
