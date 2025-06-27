package com.colegio.prevost.util.mapper;

import org.mapstruct.Mapper;

import com.colegio.prevost.dto.WorkerDTO;
import com.colegio.prevost.model.Worker;

@Mapper(componentModel = "spring")
public interface WorkerMapper {

     WorkerDTO toWorkerDTO(Worker worker);
     Worker toWorker(WorkerDTO workerDTO);
}
