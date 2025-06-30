package com.colegio.prevost.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.colegio.prevost.dto.GradeRecordDTO;
import com.colegio.prevost.service.delegate.GradeRecordDeletage;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/grade-records")
@RequiredArgsConstructor
public class GradeRecordController {

    private final GradeRecordDeletage gradeRecordDelegate;

    @GetMapping
    public ResponseEntity<List<GradeRecordDTO>> getAllGradeRecords() {
        List<GradeRecordDTO> records = gradeRecordDelegate.getAllGradeRecords();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeRecordDTO> getGradeRecordById(@PathVariable String id) {
        GradeRecordDTO dto = gradeRecordDelegate.getGradeRecordById(id);
        return dto != null
                ? ResponseEntity.ok(dto)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<GradeRecordDTO> createGradeRecord(@RequestBody GradeRecordDTO gradeRecord) {
        GradeRecordDTO created = gradeRecordDelegate.createGradeRecord(gradeRecord);
        return ResponseEntity
                .created(URI.create("/api/grade-records/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeRecordDTO> updateGradeRecord(
            @PathVariable String id,
            @RequestBody GradeRecordDTO gradeRecord) {

        GradeRecordDTO updated = gradeRecordDelegate.updateGradeRecord(id, gradeRecord);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGradeRecord(@PathVariable String id) {
        gradeRecordDelegate.deleteGradeRecord(id);
        return ResponseEntity.noContent().build();
    }
}
