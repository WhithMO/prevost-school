package com.colegio.prevost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colegio.prevost.model.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

}
