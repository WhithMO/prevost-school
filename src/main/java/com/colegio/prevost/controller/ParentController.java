package com.colegio.prevost.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.colegio.prevost.dto.ParentDTO;
import com.colegio.prevost.service.delegate.ParentDeletage;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/parents")
@RequiredArgsConstructor
public class ParentController {

    private final ParentDeletage parentDelegate;

    @GetMapping
    public ResponseEntity<List<ParentDTO>> getAllParents() {
        List<ParentDTO> parents = parentDelegate.getAllParents();
        return ResponseEntity.ok(parents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParentDTO> getParentById(@PathVariable String username) {
        ParentDTO dto = parentDelegate.getParentByUsername(username);
        return dto != null
                ? ResponseEntity.ok(dto)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ParentDTO> createParent(@RequestBody ParentDTO parent) {
        ParentDTO created = parentDelegate.createParent(parent);
        return ResponseEntity
                .created(URI.create("/api/parents/" + created.getUsername()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParentDTO> updateParent(
            @PathVariable String username,
            @RequestBody ParentDTO parent) {

        ParentDTO updated = parentDelegate.updateParent(username, parent);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable String username) {
        parentDelegate.deleteParent(username);
        return ResponseEntity.noContent().build();
    }
}
