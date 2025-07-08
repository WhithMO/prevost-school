package com.colegio.prevost.service.delegate.impl;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.WorkerDTO;
import com.colegio.prevost.model.User;
import com.colegio.prevost.model.Worker;
import com.colegio.prevost.repository.UserRepository;
import com.colegio.prevost.repository.WorkerRepository;
import com.colegio.prevost.service.delegate.WorkerDeletage;
import com.colegio.prevost.util.mapper.WorkerMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkerDeletageImpl implements WorkerDeletage {

    private final WorkerRepository workerRepository;
    private final UserRepository userRepository;
    private final WorkerMapper mapper;

    @Override
    public WorkerDTO getWorkerByUsername(String username) {
        try {
            Worker worker = workerRepository.findByUserUsername(username);
            if (worker == null) {
                throw new ServiceException("Recurso no encontrado: Worker username=" + username);
            }
            User user = userRepository.findById(worker.getUserId())
                    .orElseThrow(() -> new ServiceException("Recurso no encontrado: User id=" + worker.getUserId()));
            return new WorkerDTO().getWorkerDTO(worker, user);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al obtener Worker username={}", username, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public List<WorkerDTO> getAllWorkers() {
        try {
            return workerRepository.findAll().stream()
                    .map(worker -> getWorkerByUsername(worker.getUser().getUsername()))
                    .toList();
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al listar Workers", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public WorkerDTO createWorker(WorkerDTO worker) {
        try {
            String contructedId = String.format("%s-%s", worker.getSurNames()
                    .toUpperCase().charAt(0), worker.getDocumentNumber());
            worker.setUsername(contructedId);
            User user = userRepository.save(new User().getUserFromDto(worker));
            Worker savedWorker = workerRepository.save(new Worker(
                    user,
                    worker.getHiringDate(),
                    worker.getTerminationDate(),
                    worker.getMobileNumber()
            ));
            return mapper.toWorkerDTO(savedWorker);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al crear Worker", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public WorkerDTO updateWorker(String username, WorkerDTO worker) {
        try {
            Worker existingWorker = workerRepository.findByUserUsername(username);
            if (existingWorker == null) {
                throw new ServiceException("Recurso no encontrado: Worker username=" + username);
            }
            User existingUser = userRepository.findById(existingWorker.getUserId())
                    .orElseThrow(() -> new ServiceException("Recurso no encontrado: User id=" + existingWorker.getUserId()));
            existingUser.setNames(worker.getNames());
            existingUser.setSurNames(worker.getSurNames());
            existingUser.setEmail(worker.getEmail());
            userRepository.save(existingUser);

            existingWorker.setMobileNumber(worker.getMobileNumber());
            existingWorker.setHiringDate(worker.getHiringDate());
            existingWorker.setTerminationDate(worker.getTerminationDate());
            workerRepository.save(existingWorker);
            return worker;
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al actualizar Worker username={}", username, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }

    }

    @Override
    public void deleteWorker(String username) {
        try {
            if (workerRepository.findByUserUsername(username) == null) {
                throw new ServiceException("Recurso no encontrado: Worker username=" + username);
            }
            workerRepository.deleteByUserUsername(username);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al eliminar Worker username={}", username, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

}
