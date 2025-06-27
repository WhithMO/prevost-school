package com.colegio.prevost.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.model.Worker;
import com.colegio.prevost.repository.WorkerRepository;
import com.colegio.prevost.service.WorkerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository repository;

    @Override
    public Worker getWorkerById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Worker> getAllWorkers() {
        return repository.findAll();
    }

    @Override
    public Worker createWorker(Worker worker) {
        return repository.save(worker);
    }

    @Override
    public Worker updateWorker(Long id, Worker worker) {
        Worker existingWorker = repository.findById(id).orElse(null);
        if (existingWorker != null) {
            existingWorker.setCode(worker.getCode());
            existingWorker.setNames(worker.getNames());
            existingWorker.setSurNames(worker.getSurNames());
            existingWorker.setEmail(worker.getEmail());
            existingWorker.setPassword(worker.getPassword());
            existingWorker.setRole(worker.getRole());
            existingWorker.setMobileNumber(worker.getMobileNumber());
            existingWorker.setHiringDate(worker.getHiringDate());
            existingWorker.setTerminationDate(worker.getTerminationDate());
            return repository.save(existingWorker);
        }
        return null;
    }

    @Override
    public void deleteWorker(Long id) {
        repository.deleteById(id);
    }

}
