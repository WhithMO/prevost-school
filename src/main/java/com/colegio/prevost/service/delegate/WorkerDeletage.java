package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.WorkerDTO;

public interface WorkerDeletage {
    WorkerDTO getWorkerByUsername(String username);
    List<WorkerDTO> getAllWorkers();
    WorkerDTO createWorker(WorkerDTO worker);
    WorkerDTO updateWorker(String username, WorkerDTO worker);
    void deleteWorker(String username);
}
