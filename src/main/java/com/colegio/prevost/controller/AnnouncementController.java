package com.colegio.prevost.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.colegio.prevost.dto.AnnouncementDTO;
import com.colegio.prevost.service.delegate.AnnouncementDeletage;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementDeletage announcementDelegate;

    @GetMapping
    public ResponseEntity<List<AnnouncementDTO>> getAllAnnouncements() {
        List<AnnouncementDTO> announcements = announcementDelegate.getAllAnnouncements();
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementDTO> getAnnouncementById(@PathVariable Long id) {
        AnnouncementDTO dto = announcementDelegate.getAnnouncementById(id);
        return dto != null
                ? ResponseEntity.ok(dto)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<AnnouncementDTO> createAnnouncement(@RequestBody AnnouncementDTO announcement) {
        AnnouncementDTO created = announcementDelegate.createAnnouncement(announcement);
        return ResponseEntity
                .created(URI.create("/api/announcements/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnouncementDTO> updateAnnouncement(
            @PathVariable Long id,
            @RequestBody AnnouncementDTO announcement) {

        AnnouncementDTO updated = announcementDelegate.updateAnnouncement(id, announcement);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        announcementDelegate.deleteAnnouncement(id);
        return ResponseEntity.noContent().build();
    }
}
