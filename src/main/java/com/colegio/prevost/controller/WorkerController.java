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

    @GetMapping("/{id}")
    public ResponseEntity<WorkerDTO> getWorkerById(@PathVariable Long id) {
        WorkerDTO dto = workerDelegate.getWorkerById(id);
        return dto != null
                ? ResponseEntity.ok(dto)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<WorkerDTO> createWorker(@RequestBody WorkerDTO worker) {
        WorkerDTO created = workerDelegate.createWorker(worker);
        return ResponseEntity
                .created(URI.create("/api/workers/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkerDTO> updateWorker(
            @PathVariable Long id,
            @RequestBody WorkerDTO worker) {

        WorkerDTO updated = workerDelegate.updateWorker(id, worker);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorker(@PathVariable Long id) {
        workerDelegate.deleteWorker(id);
        return ResponseEntity.noContent().build();
    }
}
