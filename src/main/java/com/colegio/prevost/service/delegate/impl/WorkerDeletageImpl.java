package com.colegio.prevost.service.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.WorkerDTO;
import com.colegio.prevost.model.User;
import com.colegio.prevost.model.Worker;
import com.colegio.prevost.repository.UserRepository;
import com.colegio.prevost.repository.WorkerRepository;
import com.colegio.prevost.service.delegate.WorkerDeletage;
import com.colegio.prevost.util.mapper.WorkerMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerDeletageImpl implements WorkerDeletage {

    private final WorkerRepository workerRepository;
    private final UserRepository userRepository;
    private final WorkerMapper mapper;

    @Override
    public WorkerDTO getWorkerByUsername(String username) {
        Worker worker = workerRepository.findByUserUsername(username);
        if (worker != null) {
            User user = userRepository.findById(worker.getUserId()).orElse(null);
            return new WorkerDTO().getWorkerDTO(worker, user);
        }
        return null;
    }

    @Override
    public List<WorkerDTO> getAllWorkers() {
        return workerRepository.findAll().stream()
                .map(worker -> getWorkerByUsername(worker.getUser().getUsername()))
                .toList();
    }

    @Override
    public WorkerDTO createWorker(WorkerDTO worker) {
        User user = userRepository.save(new User().getUserFromDto(worker));
        Worker savedWorker = workerRepository.save(new Worker(
                user,
                worker.getHiringDate(),
                worker.getTerminationDate(),
                worker.getMobileNumber()
        ));
        return mapper.toWorkerDTO(savedWorker);
    }

    @Override
    public WorkerDTO updateWorker(String username, WorkerDTO worker) {
        Worker existingWorker = workerRepository.findByUserUsername(username);
        if (existingWorker != null) {
            User existingUser = userRepository.findById(existingWorker.getUserId()).orElse(null);
            existingUser.setNames(worker.getNames());
            existingUser.setSurNames(worker.getSurNames());
            existingUser.setEmail(worker.getEmail());
            userRepository.save(existingUser);

            existingWorker.setMobileNumber(worker.getMobileNumber());
            existingWorker.setHiringDate(worker.getHiringDate());
            existingWorker.setTerminationDate(worker.getTerminationDate());
            workerRepository.save(existingWorker);
            return worker;
        }
        return null;
    }

    @Override
    public void deleteWorker(String username) {
        workerRepository.deleteByUserUsername(username);
    }

}
