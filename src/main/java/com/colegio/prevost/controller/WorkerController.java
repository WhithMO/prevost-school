package com.colegio.prevost.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.colegio.prevost.dto.WorkerDTO;
import com.colegio.prevost.service.delegate.WorkerDeletage;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerDeletage workerDelegate;

    @GetMapping
    public ResponseEntity<List<WorkerDTO>> getAllWorkers() {
        List<WorkerDTO> workers = workerDelegate.getAllWorkers();
        return ResponseEntity.ok(workers);
    }

    @GetMapping("/{username}")
    public ResponseEntity<WorkerDTO> getWorkerById(@PathVariable String username) {
        WorkerDTO dto = workerDelegate.getWorkerByUsername(username);
        return dto != null
                ? ResponseEntity.ok(dto)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<WorkerDTO> createWorker(@RequestBody WorkerDTO worker) {
        WorkerDTO created = workerDelegate.createWorker(worker);
        return ResponseEntity
                .created(URI.create("/api/workers/" + created.getUsername()))
                .body(created);
    }

    @PutMapping("/{username}")
    public ResponseEntity<WorkerDTO> updateWorker(
            @PathVariable String username,
            @RequestBody WorkerDTO worker) {

        WorkerDTO updated = workerDelegate.updateWorker(username, worker);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteWorker(@PathVariable String username) {
        workerDelegate.deleteWorker(username);
        return ResponseEntity.noContent().build();
    }
}
