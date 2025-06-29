package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.WorkerDTO;

public interface WorkerDeletage {
    WorkerDTO getWorkerById(Long id);
    List<WorkerDTO> getAllWorkers();
    WorkerDTO createWorker(WorkerDTO worker);
    WorkerDTO updateWorker(Long id, WorkerDTO worker);
    void deleteWorker(Long id);
}
