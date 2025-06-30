package com.colegio.prevost.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.colegio.prevost.dto.ParentStudentDTO;
import com.colegio.prevost.service.delegate.ParentStudentDeletage;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/parent-students")
@RequiredArgsConstructor
public class ParentStudentController {

    private final ParentStudentDeletage delegate;

    @GetMapping
    public ResponseEntity<List<ParentStudentDTO>> getAllParentStudents() {
        return ResponseEntity.ok(delegate.getAllParentStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParentStudentDTO> getParentStudentById(@PathVariable String id) {
        ParentStudentDTO dto = delegate.getParentStudentById(id);
        return dto != null
                ? ResponseEntity.ok(dto)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ParentStudentDTO> createParentStudent(@RequestBody ParentStudentDTO parentStudent) {
        ParentStudentDTO created = delegate.createParentStudent(parentStudent);
        return ResponseEntity
                .created(URI.create("/api/parent-students/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParentStudentDTO> updateParentStudent(
            @PathVariable String id,
            @RequestBody ParentStudentDTO parentStudent) {

        ParentStudentDTO updated = delegate.updateParentStudent(id, parentStudent);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParentStudent(@PathVariable String id) {
        delegate.deleteParentStudent(id);
        return ResponseEntity.noContent().build();
    }
}
