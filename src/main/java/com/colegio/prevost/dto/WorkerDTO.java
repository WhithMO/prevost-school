package com.colegio.prevost.dto;

import java.time.LocalDate;

import com.colegio.prevost.model.Worker;
import com.colegio.prevost.model.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WorkerDTO extends UserDTO {

    private LocalDate hiringDate;
    private LocalDate terminationDate;
    private Integer mobileNumber;

    public WorkerDTO getWorkerDTO(Worker worker, User user) {
        WorkerDTO workerDTO = new WorkerDTO();
        workerDTO.setNames(user.getNames());
        workerDTO.setSurNames(user.getSurNames());
        workerDTO.setEmail(user.getEmail());
        workerDTO.setHiringDate(worker.getHiringDate());
        workerDTO.setTerminationDate(worker.getTerminationDate());
        workerDTO.setMobileNumber(worker.getMobileNumber());
        return workerDTO;
    }
}
