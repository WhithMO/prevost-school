package com.colegio.prevost.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colegio.prevost.dto.AnnouncementDTO;
import com.colegio.prevost.service.delegate.AnnouncementDeletage;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementDeletage service;

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementDTO> getAnnouncementById(@PathVariable Long id) {
        AnnouncementDTO announcement = service.getAnnouncementById(id);
        return announcement != null ? ResponseEntity.ok(announcement) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<AnnouncementDTO>> getAllAnnouncements() {
        return ResponseEntity.ok(service.getAllAnnouncements());
    }

    @PostMapping
    public ResponseEntity<AnnouncementDTO> createAnnouncement(@RequestBody AnnouncementDTO announcement) {
        return ResponseEntity.ok(service.createAnnouncement(announcement));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnouncementDTO> updateAnnouncement(@PathVariable Long id, @RequestBody AnnouncementDTO announcement) {
        AnnouncementDTO updatedAnnouncement = service.updateAnnouncement(id, announcement);
        return updatedAnnouncement != null ? ResponseEntity.ok(updatedAnnouncement) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        service.deleteAnnouncement(id);
        return ResponseEntity.noContent().build();
    }
}
