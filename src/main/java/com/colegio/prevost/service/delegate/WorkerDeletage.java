package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.WorkerDTO;
import com.colegio.prevost.model.User;
import com.colegio.prevost.model.Worker;

public interface WorkerDeletage {
    Worker getWorkerById(Long id);
    List<Worker> getAllWorkers();
    Worker createWorker(Worker worker);
    WorkerDTO updateWorker(Long id, Worker worker, User user);
    void deleteWorker(Long id);
}
