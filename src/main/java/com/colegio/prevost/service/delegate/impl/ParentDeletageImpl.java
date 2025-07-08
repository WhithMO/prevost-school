package com.colegio.prevost.service.delegate.impl;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.ParentDTO;
import com.colegio.prevost.model.Parent;
import com.colegio.prevost.model.User;
import com.colegio.prevost.repository.ParentRepository;
import com.colegio.prevost.repository.UserRepository;
import com.colegio.prevost.service.delegate.ParentDeletage;
import com.colegio.prevost.util.mapper.ParentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParentDeletageImpl implements ParentDeletage {

    private final ParentRepository parentRepository;
    private final UserRepository userRepository;
    private final ParentMapper mapper;

    public ParentDTO getParentByUsername(String username) {
        try {
            Parent parent = parentRepository.findByUserUsername(username);
            if (parent == null) {
                throw new ServiceException("Recurso no encontrado: Parent username=" + username);
            }
            User user = userRepository.findById(parent.getUserId())
                    .orElseThrow(() -> new ServiceException("Recurso no encontrado: User id=" + parent.getUserId()));
            return new ParentDTO().getParentDTOFromEntity(parent, user);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al obtener Parent username={}", username, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public List<ParentDTO> getAllParents() {
        try {
            return parentRepository.findAll().stream()
                    .map(parent -> getParentByUsername(parent.getUser().getUsername()))
                    .toList();
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al listar Parents", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public ParentDTO createParent(ParentDTO parent) {
        try {
            String contructedId = getContructedId(parent);
            parent.setUsername(contructedId);
            User user = userRepository.save(new User().getUserFromDto(parent));
            Parent savedParent = parentRepository.save(new Parent(user, parent.getMobileNumber()));
            return mapper.toDto(savedParent);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al crear Parent", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public ParentDTO updateParent(String username, ParentDTO parent) {
        try {
            Parent existingParent = parentRepository.findByUserUsername(username);
            if (existingParent == null) {
                throw new ServiceException("Recurso no encontrado: Parent username=" + username);
            }
            User existingUser = userRepository.findById(existingParent.getUserId())
                    .orElseThrow(() -> new ServiceException("Recurso no encontrado: User id=" + existingParent.getUserId()));
            existingUser.setNames(parent.getNames());
            existingUser.setSurNames(parent.getSurNames());
            existingUser.setEmail(parent.getEmail());
            userRepository.save(existingUser);

            existingParent.setMobileNumber(parent.getMobileNumber());
            parentRepository.save(existingParent);
            return parent;
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al actualizar Parent username={}", username, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public void deleteParent(String username) {
        try {
            Parent existing = parentRepository.findByUserUsername(username);
            if (existing == null) {
                throw new ServiceException("Recurso no encontrado: Parent username=" + username);
            }
            parentRepository.deleteByUserUsername(username);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al eliminar Parent username={}", username, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    private String getContructedId(ParentDTO parent) {
        return String.format("%s-%s", parent.getSurNames()
                .toUpperCase().charAt(0), parent.getDocumentNumber());
    }

}
