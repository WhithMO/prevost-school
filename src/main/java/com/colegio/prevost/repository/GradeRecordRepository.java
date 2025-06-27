package com.colegio.prevost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colegio.prevost.model.GradeRecord;

public interface GradeRecordRepository extends JpaRepository<GradeRecord, Long> {

}
