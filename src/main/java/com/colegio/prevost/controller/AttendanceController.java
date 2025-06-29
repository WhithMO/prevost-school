package com.colegio.prevost.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.colegio.prevost.dto.AttendanceDTO;
import com.colegio.prevost.service.delegate.AttendanceDeletage;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceDeletage attendanceDelegate;

    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> getAllAttendances() {
        List<AttendanceDTO> attendances = attendanceDelegate.getAllAttendances();
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDTO> getAttendanceById(@PathVariable Long id) {
        AttendanceDTO dto = attendanceDelegate.getAttendanceById(id);
        return dto != null
                ? ResponseEntity.ok(dto)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<AttendanceDTO> createAttendance(@RequestBody AttendanceDTO attendance) {
        AttendanceDTO created = attendanceDelegate.createAttendance(attendance);
        return ResponseEntity
                .created(URI.create("/api/attendances/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendanceDTO> updateAttendance(
            @PathVariable Long id,
            @RequestBody AttendanceDTO attendance) {

        AttendanceDTO updated = attendanceDelegate.updateAttendance(id, attendance);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendanceDelegate.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}