package com.colegio.prevost.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.WorkerDTO;
import com.colegio.prevost.model.User;
import com.colegio.prevost.model.Worker;
import com.colegio.prevost.repository.UserRepository;
import com.colegio.prevost.repository.WorkerRepository;
import com.colegio.prevost.service.WorkerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;
    private final UserRepository userRepository;

    @Override
    public Worker getWorkerById(Long id) {
        return workerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    @Override
    public Worker createWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    @Override
    public WorkerDTO updateWorker(Long id, Worker worker, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        Worker existingWorker = workerRepository.findById(id).orElse(null);

        if (existingUser != null) {
            existingUser.setCode(user.getCode());
            existingUser.setNames(user.getNames());
            existingUser.setSurNames(user.getSurNames());
            existingUser.setEmail(user.getEmail());
            userRepository.save(existingUser);
        }
        if (existingWorker != null) {
            existingWorker.setMobileNumber(worker.getMobileNumber());
            existingWorker.setHiringDate(worker.getHiringDate());
            existingWorker.setTerminationDate(worker.getTerminationDate());
            workerRepository.save(existingWorker);
        }
        return new WorkerDTO().getWorkerDTO(worker, user);
    }

    @Override
    public void deleteWorker(Long id) {
        workerRepository.deleteById(id);
    }

}
