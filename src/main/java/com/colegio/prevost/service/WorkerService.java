package com.colegio.prevost.service;

import java.util.List;

import com.colegio.prevost.model.Worker;

public interface WorkerService {
    Worker getWorkerById(Long id);
    List<Worker> getAllWorkers();
    Worker createWorker(Worker worker);
    Worker updateWorker(Long id, Worker worker);
    void deleteWorker(Long id);
}
